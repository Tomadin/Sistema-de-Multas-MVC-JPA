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

    public ArrayList<ActaDeConstatacion> obtenerActas() {
        ArrayList<ActaDeConstatacion> actas = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            this.conectar();

            String query = "SELECT * FROM Actadeconstatacion";

            this.consulta = this.conn.prepareStatement(query);

            resultSet = consulta.executeQuery();

            while (resultSet.next()) {
                ActaDeConstatacion acta = new ActaDeConstatacion();
// ID del acta
                acta.setIdActa(resultSet.getInt("id"));

                // === Fecha de constatación (java.util.Date) ===
                java.sql.Date fechaConst = resultSet.getDate("fecha_constatacion");
                acta.setFechaDeLabrado(new java.util.Date(fechaConst.getTime()));

                // === Fecha de vencimiento (java.util.Date) ===
                java.sql.Date fechaVto = resultSet.getDate("fecha_vencimiento_acta");
                acta.setFechaVtoPagoVolun(new java.util.Date(fechaVto.getTime()));

                // === Hora de constatación (LocalDateTime) ===
                Timestamp tsHora = resultSet.getTimestamp("hora_constatacion");
                acta.setHoraDeLabrado(
                        tsHora != null ? tsHora.toLocalDateTime() : null
                );

                // === Campos simples ===
                acta.setLugarDeConstatacion(resultSet.getString("lugar"));
                acta.setObservaciones(resultSet.getString("observaciones"));

                // === Relación OrganizacionEstatal (1..) ===
                OrganizacionEstatal org = new OrganizacionEstatal();
                org.setId(resultSet.getInt("organizacion_id"));
                acta.setOrganizacionEstatal(org);

                // === Relación Vehículo ===
                Vehiculo veh = new Vehiculo(); //solo puse el getter y setter, no guarde en la BBDD
                veh.setId(resultSet.getInt("vehiculo_id"));
                acta.setVehiculo(veh);

                // === Estado del Acta ===
                EstadoDelActa estado = new EstadoDelActa();
                
                acta.setEstadoDelActa(estado);

                // === Autoridad de constatación ===
                AutoridadDeConstatacion autoridad = new AutoridadDeConstatacion();
                autoridad.setDni(resultSet.getInt("autoridad_id"));
                acta.setAutoridadDeConstatacion(autoridad);

                // === Licencia ===
                Licencia licencia = new Licencia();
                licencia.setIdLicencia(resultSet.getInt("licencia_id"));
                acta.setLicencia(licencia);

                // === Ruta ===
                Ruta ruta = new Ruta();
                ruta.setId(resultSet.getInt("id"));
                acta.setRuta(ruta);

                // === Infracciones (se cargan luego en otro método) ===
                acta.setInfracciones(new ArrayList<>());

                actas.add(acta);

            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ActaDeConstatacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(resultSet);
        }

        return actas;

    }

}
