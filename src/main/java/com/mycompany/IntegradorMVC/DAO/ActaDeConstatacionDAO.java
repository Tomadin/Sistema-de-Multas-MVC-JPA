package com.mycompany.IntegradorMVC.DAO;

import com.mycompany.IntegradorMVC.modelo.ActaDeConstatacion;
import com.mycompany.IntegradorMVC.modelo.AutoridadDeConstatacion;
import com.mycompany.IntegradorMVC.modelo.Conductor;
import com.mycompany.IntegradorMVC.modelo.EstadoDelActa;
import com.mycompany.IntegradorMVC.modelo.Infraccion;
import com.mycompany.IntegradorMVC.modelo.Licencia;
import com.mycompany.IntegradorMVC.modelo.Marca;
import com.mycompany.IntegradorMVC.modelo.Modelo;
import com.mycompany.IntegradorMVC.modelo.OrganizacionEstatal;
import com.mycompany.IntegradorMVC.modelo.Ruta;
import com.mycompany.IntegradorMVC.modelo.TipoDeInfraccion;
import com.mycompany.IntegradorMVC.modelo.TipoRuta;
import com.mycompany.IntegradorMVC.modelo.Vehiculo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActaDeConstatacionDAO extends SQLQuery {

    public boolean guardarActa(ActaDeConstatacion acta) {
        try {
            this.conectar();
            this.conn.setAutoCommit(false);

            // 1. GUARDAR TIPO DE RUTA
            int tipoRutaId = guardarTipoRuta(acta.getRuta().getTipoRuta());

            // 2. GUARDAR RUTA
            int rutaId = guardarRuta(acta.getRuta(), tipoRutaId);

            // 3. GUARDAR MODELO
            int modeloId = guardarModelo(acta.getVehiculo().getMarca().getModelo());

            // 4. GUARDAR MARCA
            int marcaId = guardarMarca(acta.getVehiculo().getMarca(), modeloId);

            // 5. GUARDAR VEHICULO
            int vehiculoId = guardarVehiculo(acta.getVehiculo(), marcaId);

            // 6. GUARDAR LICENCIA
            int licenciaId = guardarLicencia(acta.getLicencia());

            // 7. GUARDAR ESTADO DEL ACTA
            int estadoActaId = guardarEstadoActa(new EstadoDelActa("Activo", "Activo"));

            // 8. GUARDAR ACTA DE CONSTATACION (principal)
            String queryActa = "INSERT INTO ActaDeConstatacion "
                    + "(fecha_constatacion, fecha_vencimiento_acta, hora_constatacion, lugar, observaciones, "
                    + "importe_total, organizacion_id, vehiculo_id, estado_acta_id, autoridad_id, licencia_id, ruta_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmtActa = this.conn.prepareStatement(queryActa, Statement.RETURN_GENERATED_KEYS);
            stmtActa.setDate(1, new java.sql.Date(acta.getFechaDeLabrado().getTime()));
            stmtActa.setDate(2, new java.sql.Date(acta.getFechaVtoPagoVolun().getTime()));
            stmtActa.setTimestamp(3, java.sql.Timestamp.valueOf(acta.getHoraDeLabrado()));
            stmtActa.setString(4, acta.getLugarDeConstatacion());
            stmtActa.setString(5, acta.getObservaciones());
            stmtActa.setDouble(6, calcularImporteTotal(acta));
            System.out.println(acta.getOrganizacionEstatal().getId());
            stmtActa.setInt(7, acta.getOrganizacionEstatal().getId());
            stmtActa.setInt(8, vehiculoId);
            stmtActa.setInt(9, estadoActaId);
            stmtActa.setInt(10, acta.getAutoridadDeConstatacion().getDni());
            stmtActa.setInt(11, licenciaId);
            stmtActa.setInt(12, rutaId);

            stmtActa.executeUpdate();

            // Obtener el ID del acta generada
            ResultSet rsActa = stmtActa.getGeneratedKeys();
            int actaId = 0;
            if (rsActa.next()) {
                actaId = rsActa.getInt(1);
            }

            // 9. GUARDAR RELACIONES CON INFRACCIONES
            for (Infraccion infraccion : acta.getInfracciones()) {
                // Primero guardar o verificar que la infracción existe
                int infraccionId = guardarInfraccion(infraccion);

                // Luego crear la relación
                String queryRelacion = "INSERT INTO ActaDeConstatacion_Infraccion (acta_id, infraccion_id) VALUES (?, ?)";
                PreparedStatement stmtRelacion = this.conn.prepareStatement(queryRelacion);
                stmtRelacion.setInt(1, actaId);
                stmtRelacion.setInt(2, infraccionId);
                stmtRelacion.executeUpdate();
            }

            this.conn.commit();
            return true;

        } catch (Exception ex) {
            try {
                if (this.conn != null) {
                    this.conn.rollback();
                }
            } catch (SQLException e) {
                Logger.getLogger(ActaDeConstatacionDAO.class.getName()).log(Level.SEVERE, "Error en rollback", e);
            }
            Logger.getLogger(ActaDeConstatacionDAO.class.getName()).log(Level.SEVERE, "Error guardando acta", ex);
            return false;
        } finally {
            try {
                if (this.conn != null) {
                    this.conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                Logger.getLogger(ActaDeConstatacionDAO.class.getName()).log(Level.SEVERE, "Error restaurando autocommit", e);
            }
            this.desconectar();
        }
    }

    // MÉTODOS AUXILIARES PARA GUARDAR ENTIDADES RELACIONADAS
    private int guardarTipoRuta(TipoRuta tipoRuta) throws SQLException {
        // Verificar si ya existe
        String queryCheck = "SELECT id FROM TipoRuta WHERE tipo = ?";
        PreparedStatement stmtCheck = this.conn.prepareStatement(queryCheck);
        stmtCheck.setString(1, tipoRuta.getNombreTipoDeRuta());
        ResultSet rs = stmtCheck.executeQuery();

        if (rs.next()) {
            return rs.getInt("id");
        }

        // Si no existe, insertar
        String query = "INSERT INTO TipoRuta (estado, tipo) VALUES (?, ?)";
        PreparedStatement stmt = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, tipoRuta.getDescTipoRuta());
        stmt.setString(2, tipoRuta.getNombreTipoDeRuta());
        stmt.executeUpdate();

        ResultSet rsKey = stmt.getGeneratedKeys();
        if (rsKey.next()) {
            return rsKey.getInt(1);
        }
        throw new SQLException("No se pudo obtener ID de TipoRuta");
    }

    private int guardarRuta(Ruta ruta, int tipoRutaId) throws SQLException {
        String query = "INSERT INTO Ruta (nombre, kilometro, tipo_ruta_id) VALUES (?, ?, ?)";
        PreparedStatement stmt = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, ruta.getNombreRuta());
        stmt.setString(2, ruta.getKmRuta());
        stmt.setInt(3, tipoRutaId);
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        throw new SQLException("No se pudo obtener ID de Ruta");
    }

    private int guardarModelo(Modelo modelo) throws SQLException {
        // Verificar si ya existe
        String queryCheck = "SELECT id FROM Modelo WHERE nombre = ?";
        PreparedStatement stmtCheck = this.conn.prepareStatement(queryCheck);
        stmtCheck.setString(1, modelo.getModeloAuto());
        ResultSet rs = stmtCheck.executeQuery();

        if (rs.next()) {
            return rs.getInt("id");
        }

        // Si no existe, insertar
        String query = "INSERT INTO Modelo (nombre) VALUES (?)";
        PreparedStatement stmt = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, modelo.getModeloAuto());
        stmt.executeUpdate();

        ResultSet rsKey = stmt.getGeneratedKeys();
        if (rsKey.next()) {
            return rsKey.getInt(1);
        }
        throw new SQLException("No se pudo obtener ID de Modelo");
    }

    private int guardarMarca(Marca marca, int modeloId) throws SQLException {
        // Verificar si ya existe
        String queryCheck = "SELECT id FROM Marca WHERE nombre = ? AND modelo_id = ?";
        PreparedStatement stmtCheck = this.conn.prepareStatement(queryCheck);
        stmtCheck.setString(1, marca.getMarcaAuto());
        stmtCheck.setInt(2, modeloId);
        ResultSet rs = stmtCheck.executeQuery();

        if (rs.next()) {
            return rs.getInt("id");
        }

        // Si no existe, insertar
        String query = "INSERT INTO Marca (nombre, modelo_id) VALUES (?, ?)";
        PreparedStatement stmt = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, marca.getMarcaAuto());
        stmt.setInt(2, modeloId);
        stmt.executeUpdate();

        ResultSet rsKey = stmt.getGeneratedKeys();
        if (rsKey.next()) {
            return rsKey.getInt(1);
        }
        throw new SQLException("No se pudo obtener ID de Marca");
    }

    private int guardarVehiculo(Vehiculo vehiculo, int marcaId) throws SQLException {
        // Verificar si ya existe por dominio
        String queryCheck = "SELECT id FROM Vehiculo WHERE dominio = ?";
        PreparedStatement stmtCheck = this.conn.prepareStatement(queryCheck);
        stmtCheck.setString(1, vehiculo.getDominio());
        ResultSet rs = stmtCheck.executeQuery();

        if (rs.next()) {
            return rs.getInt("id");
        }

        // Si no existe, insertar
        String query = "INSERT INTO Vehiculo (color, dominio, anio, marca_id) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, vehiculo.getColor());
        stmt.setString(2, vehiculo.getDominio());
        stmt.setInt(3, vehiculo.getAnioPatentamiento());
        stmt.setInt(4, marcaId);
        stmt.executeUpdate();

        ResultSet rsKey = stmt.getGeneratedKeys();
        if (rsKey.next()) {
            return rsKey.getInt(1);
        }
        throw new SQLException("No se pudo obtener ID de Vehiculo");
    }

    private int guardarLicencia(Licencia licencia) throws SQLException {

        // 1. GUARDAR / VERIFICAR CONDUCTOR
        Conductor conductor = licencia.getConductor();
        int conductorDni = guardarConductor(conductor); // <-- NUEVO
        // Esto devuelve el DNI guardado (o el mismo si ya existía)

        // 2. Verificar si ya existe la licencia por número
        String queryCheck = "SELECT id FROM Licencia WHERE numero_licencia = ?";
        PreparedStatement stmtCheck = this.conn.prepareStatement(queryCheck);
        stmtCheck.setInt(1, licencia.getIdLicencia());
        ResultSet rs = stmtCheck.executeQuery();

        if (rs.next()) {
            return rs.getInt("id");  // Ya existe
        }

        // 3. Insertar licencia con conductor_dni
        String query = "INSERT INTO Licencia (numero_licencia, fecha_vencimiento, puntos, conductor_dni) "
                + "VALUES (?, ?, ?, ?)";

        PreparedStatement stmt = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, licencia.getIdLicencia());
        stmt.setDate(2, new java.sql.Date(licencia.getFechaDeVto().getTime()));
        stmt.setInt(3, licencia.getPuntosInicialesLicencia());
        stmt.setInt(4, conductorDni);  // <-- RELACIÓN FK

        stmt.executeUpdate();

        ResultSet rsKey = stmt.getGeneratedKeys();
        if (rsKey.next()) {
            return rsKey.getInt(1);
        }

        throw new SQLException("No se pudo obtener ID de Licencia");
    }

    private int guardarConductor(Conductor conductor) throws SQLException {

        String queryCheck = "SELECT dni FROM Conductor WHERE dni = ?";
        PreparedStatement stmtCheck = this.conn.prepareStatement(queryCheck);
        stmtCheck.setInt(1, conductor.getDni());
        ResultSet rs = stmtCheck.executeQuery();

        if (rs.next()) {
            return conductor.getDni();
        }

        String queryInsert = "INSERT INTO Conductor (dni, nombre, apellido, genero, domicilio) "
                + "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement stmt = this.conn.prepareStatement(queryInsert);
        stmt.setInt(1, conductor.getDni());
        stmt.setString(2, conductor.getNombre());
        stmt.setString(3, conductor.getApellido());
        stmt.setString(4, conductor.getGenero());
        stmt.setString(5, conductor.getDomicilio());

        stmt.executeUpdate();

        return conductor.getDni(); // La PK es DNI
    }

    private int guardarEstadoActa(EstadoDelActa estado) throws SQLException {
        // Verificar si ya existe
        String queryCheck = "SELECT id FROM EstadoDelActa WHERE estado = ?";
        PreparedStatement stmtCheck = this.conn.prepareStatement(queryCheck);
        stmtCheck.setString(1, estado.getDescripcionEstadoActa());
        ResultSet rs = stmtCheck.executeQuery();

        if (rs.next()) {
            return rs.getInt("id");
        }

        // Si no existe, insertar
        String query = "INSERT INTO EstadoDelActa (estado, descripcion) VALUES (?, ?)";
        PreparedStatement stmt = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, estado.getNombreEstadoActa());
        stmt.setString(2, estado.getDescripcionEstadoActa());
        stmt.executeUpdate();

        ResultSet rsKey = stmt.getGeneratedKeys();
        if (rsKey.next()) {
            return rsKey.getInt(1);
        }
        throw new SQLException("No se pudo obtener ID de EstadoDelActa");
    }

    private int guardarInfraccion(Infraccion infraccion) throws SQLException {
        String query = "INSERT INTO Infraccion (descripcion, importeInfraccion) VALUES (?, ?)";
        PreparedStatement stmt = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, infraccion.getDescripcionInfraccion());
        stmt.setDouble(2, infraccion.getImporteInfraccion());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        int infraccionId = 0;
        if (rs.next()) {
            infraccionId = rs.getInt(1);
        }

        // Guardar relaciones con tipos de infracción
        for (TipoDeInfraccion tipo : infraccion.getInfraccionNomenclanda()) {
            String queryRelacion = "INSERT INTO Infraccion_TipoInfraccion (infraccion_id, tipo_infraccion_id) VALUES (?, ?)";
            PreparedStatement stmtRelacion = this.conn.prepareStatement(queryRelacion);
            stmtRelacion.setInt(1, infraccionId);
            stmtRelacion.setInt(2, tipo.getId_infrac());
            stmtRelacion.executeUpdate();
        }

        return infraccionId;
    }

//    private int guardarConductor(Conductor conductor) throws SQLException{
//        
//    }
    private double calcularImporteTotal(ActaDeConstatacion acta) {
        double total = 0.0;
        for (Infraccion infraccion : acta.getInfracciones()) {
            total += infraccion.getImporteInfraccion();
        }
        return total;
    }

    public ArrayList<ActaDeConstatacion> obtenerActas() throws SQLException {
        ArrayList<ActaDeConstatacion> actas = new ArrayList<>();
        ResultSet rs = null;

        try {
            this.conectar();

            String query
                    = "SELECT \n"
                    + "    a.id,\n"
                    + "    a.fecha_constatacion,\n"
                    + "    a.fecha_vencimiento_acta,\n"
                    + "    a.hora_constatacion,\n"
                    + "    a.lugar,\n"
                    + "    a.observaciones,\n"
                    + "    a.importe_total,\n"
                    + "\n"
                    + "    -- Organización\n"
                    + "    o.id AS org_id,\n"
                    + "    o.nombreOrganizacion AS organizacion_nombre,\n"
                    + "\n"
                    + "    -- Vehículo\n"
                    + "    v.id AS veh_id,\n"
                    + "    v.dominio AS veh_dominio,\n"
                    + "\n"
                    + "    -- Estado del acta\n"
                    + "    e.id AS estado_id,\n"
                    + "    e.descripcion AS estado_descripcion,\n"
                    + "\n"
                    + "    -- Autoridad\n"
                    + "    au.dni AS autoridad_id,\n"
                    + "    au.nombre AS autoridad_nombre,\n"
                    + "\n"
                    + "    -- Licencia\n"
                    + "    l.id AS licencia_id,\n"
                    + "\n"
                    + "    -- Ruta\n"
                    + "    r.id AS ruta_id,\n"
                    + "    r.nombre AS ruta_nombre\n"
                    + "\n"
                    + "FROM actadeconstatacion a\n"
                    + "LEFT JOIN organizacion o ON a.organizacion_id = o.id\n"
                    + "LEFT JOIN vehiculo v ON a.vehiculo_id = v.id\n"
                    + "LEFT JOIN estadodelacta e ON a.estado_acta_id = e.id\n"
                    + "LEFT JOIN autoridad au ON a.autoridad_id = au.dni\n"
                    + "LEFT JOIN licencia l ON a.licencia_id = l.id\n"
                    + "LEFT JOIN ruta r ON a.ruta_id = r.id";

            this.consulta = this.conn.prepareStatement(query);
            rs = consulta.executeQuery();

            while (rs.next()) {

                ActaDeConstatacion acta = new ActaDeConstatacion();

                // Datos base
                acta.setIdActa(rs.getInt("id"));

                java.sql.Date f1 = rs.getDate("fecha_constatacion");
                acta.setFechaDeLabrado(f1 != null ? new java.util.Date(f1.getTime()) : null);

                java.sql.Date f2 = rs.getDate("fecha_vencimiento_acta");
                acta.setFechaVtoPagoVolun(f2 != null ? new java.util.Date(f2.getTime()) : null);

                Timestamp ts = rs.getTimestamp("hora_constatacion");
                acta.setHoraDeLabrado(ts != null ? ts.toLocalDateTime() : null);

                acta.setLugarDeConstatacion(rs.getString("lugar"));
                acta.setObservaciones(rs.getString("observaciones"));

                // Organización
                if (rs.getInt("org_id") != 0) {
                    OrganizacionEstatal org = new OrganizacionEstatal();
                    org.setId(rs.getInt("org_id"));
                    org.setNombreOrganizacion(rs.getString("organizacion_nombre"));
                    acta.setOrganizacionEstatal(org);
                }

                // Vehículo
                if (rs.getInt("veh_id") != 0) {
                    Vehiculo v = new Vehiculo();
                    v.setId(rs.getInt("veh_id"));
                    v.setDominio(rs.getString("veh_dominio"));
                    acta.setVehiculo(v);
                }

                // Estado
                if (rs.getInt("estado_id") != 0) {
                    EstadoDelActa e = new EstadoDelActa();
                    e.setId(rs.getInt("estado_id"));
                    e.setDescripcionEstadoActa(rs.getString("estado_descripcion"));
                    acta.setEstadoDelActa(e);
                }

                // Autoridad
                if (rs.getInt("autoridad_id") != 0) {
                    AutoridadDeConstatacion au = new AutoridadDeConstatacion();
                    au.setDni(rs.getInt("autoridad_id"));
                    au.setNombre(rs.getString("autoridad_nombre"));
                    acta.setAutoridadDeConstatacion(au);
                }

                // Licencia
                if (rs.getInt("licencia_id") != 0) {
                    Licencia l = new Licencia();
                    l.setIdLicencia(rs.getInt("licencia_id"));
                    acta.setLicencia(l);
                }

                // Ruta
                if (rs.getInt("ruta_id") != 0) {
                    Ruta r = new Ruta();
                    r.setId(rs.getInt("ruta_id"));
                    r.setNombreRuta(rs.getString("ruta_nombre"));
                    acta.setRuta(r);
                }
                actas.add(acta);
                System.out.println(acta.toString());
            }
        } catch (Exception ex) {
            Logger.getLogger(ActaDeConstatacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(rs);
        }
        
        return actas;
    }
}
