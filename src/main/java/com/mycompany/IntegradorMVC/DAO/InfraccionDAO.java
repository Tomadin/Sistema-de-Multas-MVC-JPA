package com.mycompany.IntegradorMVC.DAO;

import com.mycompany.IntegradorMVC.modelo.Infraccion;
import com.mycompany.IntegradorMVC.modelo.TipoDeInfraccion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InfraccionDAO extends SQLQuery {

    public boolean crearInfraccion(Infraccion infraccion) throws SQLException {
        try {
            this.conectar();
            this.conn.setAutoCommit(false);

            String queryInfraccion = "INSERT INTO Infraccion (descripcion, importeInfraccion) VALUES (?, ?)";
            this.consulta = this.conn.prepareStatement(queryInfraccion, Statement.RETURN_GENERATED_KEYS);
            consulta.setString(1, infraccion.getDescripcionInfraccion());
            consulta.setDouble(2, infraccion.getImporteInfraccion());

            int result = consulta.executeUpdate();

            ResultSet rs = consulta.getGeneratedKeys();
            int infraccionId = 0;
            if (rs.next()) {
                infraccionId = rs.getInt(1);
            }

            String queryRelacion = "INSERT INTO Infraccion_TipoInfraccion (infraccion_id, tipo_infraccion_id) VALUES (?, ?)";
            PreparedStatement stmtRelacion = this.conn.prepareStatement(queryRelacion);

            for (TipoDeInfraccion tipo : infraccion.getInfraccionNomenclanda()) {
                stmtRelacion.setInt(1, infraccionId);
                stmtRelacion.setInt(2, tipo.getId_infrac());
                stmtRelacion.addBatch();
            }

            stmtRelacion.executeBatch();

            this.conn.commit(); // Confirmar transacción
            return result > 0;

        } catch (Exception ex) {
            if (this.conn != null) {
                this.conn.rollback(); // Revertir en caso de error
            }
            Logger.getLogger(InfraccionDAO.class.getName()).log(Level.SEVERE, "Problema creando Infracción", ex);
            return false;
        } finally {
            if (this.conn != null) {
                this.conn.setAutoCommit(true);
            }
        }
    }

    public boolean actualizarInfraccion(int infraccionId, Infraccion infraccion) {
        try {
            this.conectar();
            this.conn.setAutoCommit(false);

            // 1. Actualizar datos básicos de la infracción
            String query = "UPDATE Infraccion SET descripcion=?, importeInfraccion=? WHERE id=?";
            this.consulta = this.conn.prepareStatement(query);
            consulta.setString(1, infraccion.getDescripcionInfraccion());
            consulta.setDouble(2, infraccion.getImporteInfraccion());
            consulta.setInt(3, infraccionId);

            int result = consulta.executeUpdate();

            // 2. Eliminar relaciones anteriores
            String deleteRelaciones = "DELETE FROM Infraccion_TipoInfraccion WHERE infraccion_id=?";
            PreparedStatement stmtDelete = this.conn.prepareStatement(deleteRelaciones);
            stmtDelete.setInt(1, infraccionId);
            stmtDelete.executeUpdate();

            // 3. Insertar nuevas relaciones
            String insertRelacion = "INSERT INTO Infraccion_TipoInfraccion (infraccion_id, tipo_infraccion_id) VALUES (?, ?)";
            PreparedStatement stmtInsert = this.conn.prepareStatement(insertRelacion);

            for (TipoDeInfraccion tipo : infraccion.getInfraccionNomenclanda()) {
                stmtInsert.setInt(1, infraccionId);
                stmtInsert.setInt(2, tipo.getId_infrac());
                stmtInsert.addBatch();
            }

            stmtInsert.executeBatch();
            this.conn.commit();
            return result > 0;

        } catch (Exception ex) {
            try {
                if (this.conn != null) {
                    this.conn.rollback();
                }
            } catch (SQLException e) {
                Logger.getLogger(InfraccionDAO.class.getName()).log(Level.SEVERE, "Error en rollback", e);
            }
            Logger.getLogger(InfraccionDAO.class.getName()).log(Level.SEVERE, "Problema actualizando Infracción", ex);
            return false;
        } finally {
            try {
                if (this.conn != null) {
                    this.conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                Logger.getLogger(InfraccionDAO.class.getName()).log(Level.SEVERE, "Error restaurando autocommit", e);
            }
            this.desconectar();
        }
    }

    public boolean eliminarInfraccion(int infraccionId) {
        try {
            this.conectar();
            this.conn.setAutoCommit(false);

            // 1. Eliminar relaciones primero (por foreign key)
            String deleteRelaciones = "DELETE FROM Infraccion_TipoInfraccion WHERE infraccion_id=?";
            PreparedStatement stmtRelaciones = this.conn.prepareStatement(deleteRelaciones);
            stmtRelaciones.setInt(1, infraccionId);
            stmtRelaciones.executeUpdate();

            // 2. Eliminar la infracción
            String query = "DELETE FROM Infraccion WHERE id=?";
            this.consulta = this.conn.prepareStatement(query);
            consulta.setInt(1, infraccionId);

            int result = consulta.executeUpdate();
            this.conn.commit();
            return result > 0;

        } catch (Exception ex) {
            try {
                if (this.conn != null) {
                    this.conn.rollback();
                }
            } catch (SQLException e) {
                Logger.getLogger(InfraccionDAO.class.getName()).log(Level.SEVERE, "Error en rollback", e);
            }
            Logger.getLogger(InfraccionDAO.class.getName()).log(Level.SEVERE, "Problema eliminando Infracción", ex);
            return false;
        } finally {
            try {
                if (this.conn != null) {
                    this.conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                Logger.getLogger(InfraccionDAO.class.getName()).log(Level.SEVERE, "Error restaurando autocommit", e);
            }
            this.desconectar();
        }
    }

    public ArrayList<Infraccion> obtenerInfracciones() {
        ArrayList<Infraccion> infracciones = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            this.conectar();

            // 1. Obtener todas las infracciones
            String query = "SELECT id, descripcion, importeInfraccion FROM Infraccion";
            this.consulta = this.conn.prepareStatement(query);
            resultSet = consulta.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String descripcion = resultSet.getString("descripcion");
                double importe = resultSet.getDouble("importeInfraccion");

                Infraccion infraccion = new Infraccion(descripcion, importe);

                // 2. Obtener los tipos de infracción asociados
                ArrayList<TipoDeInfraccion> tipos = obtenerTiposDeInfraccion(id);
                for (TipoDeInfraccion tipo : tipos) {
                    infraccion.addInfraccionNomenclada(tipo);
                }

                infracciones.add(infraccion);
            }

        } catch (Exception ex) {
            Logger.getLogger(InfraccionDAO.class.getName()).log(Level.SEVERE, "Error buscando infracciones", ex);
        } finally {
            this.desconectar(resultSet);
        }

        return infracciones;
    }

    public ArrayList<TipoDeInfraccion> obtenerTiposSeleccionables() {
        ArrayList<TipoDeInfraccion> tipos = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            this.conectar();

            // Asumo que tu tabla se llama 'TipoDeInfraccion' o tiene los campos necesarios.
            String query = "SELECT id_infraccion, descripcionInfraccion, tipoGravedad, importeAsignacion, porcentajeDescuento FROM TipoDeInfraccion";

            this.consulta = this.conn.prepareStatement(query);
            resultSet = consulta.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id_infraccion");
                String descripcion = resultSet.getString("descripcionInfraccion");
                String gravedad = resultSet.getString("tipoGravedad");
                double importe = resultSet.getDouble("importeAsignacion");
                double descuento = resultSet.getDouble("porcentajeDescuento");

                TipoDeInfraccion tipo = new TipoDeInfraccion(
                        id,
                        descripcion,
                        gravedad,
                        importe,
                        descuento
                );

                tipos.add(tipo);
            }

        } catch (Exception ex) {
            Logger.getLogger(InfraccionDAO.class.getName()).log(Level.SEVERE, "Error buscando tipos de infraccion", ex);
        } finally {
            this.desconectar(resultSet);
        }

        return tipos;
    }

    public Infraccion buscarInfraccionPorId(int infraccionId) {
        ResultSet resultSet = null;
        Infraccion infraccion = null;

        try {
            this.conectar();

            String query = "SELECT id, descripcion, importeInfraccion FROM Infraccion WHERE id=?";
            this.consulta = this.conn.prepareStatement(query);
            consulta.setInt(1, infraccionId);

            resultSet = consulta.executeQuery();

            if (resultSet.next()) {
                String descripcion = resultSet.getString("descripcion");
                double importe = resultSet.getDouble("importeInfraccion");

                infraccion = new Infraccion(descripcion, importe);

                // Obtener tipos asociados
                ArrayList<TipoDeInfraccion> tipos = obtenerTiposDeInfraccion(infraccionId);
                for (TipoDeInfraccion tipo : tipos) {
                    infraccion.addInfraccionNomenclada(tipo);
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(InfraccionDAO.class.getName()).log(Level.SEVERE, "Error buscando infracción por ID", ex);
        } finally {
            this.desconectar(resultSet);
        }

        return infraccion;
    }

    public boolean infraccionExiste(int infraccionId) {
        ResultSet resultSet = null;

        try {
            this.conectar();
            String query = "SELECT COUNT(*) FROM Infraccion WHERE id=?";

            this.consulta = this.conn.prepareStatement(query);
            consulta.setInt(1, infraccionId);
            resultSet = consulta.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

        } catch (Exception ex) {
            Logger.getLogger(InfraccionDAO.class.getName()).log(Level.SEVERE, "Error verificando existencia de infracción", ex);
        } finally {
            this.desconectar(resultSet);
        }

        return false;
    }

    private ArrayList<TipoDeInfraccion> obtenerTiposDeInfraccion(int infraccionId) {
        ArrayList<TipoDeInfraccion> tipos = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            String query = "SELECT t.id_infrac, t.descripcionInfraccion, t.tipoGravedad, "
                    + "t.importeAsignadoInfraccion, t.porcentajeDescuento "
                    + "FROM TipoDeInfraccion t "
                    + "INNER JOIN Infraccion_TipoInfraccion it ON t.id_infrac = it.tipo_infraccion_id "
                    + "WHERE it.infraccion_id = ?";

            stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, infraccionId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                TipoDeInfraccion tipo = new TipoDeInfraccion(
                        rs.getInt("id_infrac"),
                        rs.getString("descripcionInfraccion"),
                        rs.getString("tipoGravedad"),
                        rs.getDouble("importeAsignadoInfraccion"),
                        rs.getDouble("porcentajeDescuento")
                );
                tipos.add(tipo);
            }

        } catch (SQLException ex) {
            Logger.getLogger(InfraccionDAO.class.getName()).log(Level.SEVERE, "Error obteniendo tipos de infracción", ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(InfraccionDAO.class.getName()).log(Level.SEVERE, "Error cerrando recursos", ex);
            }
        }

        return tipos;
    }

}
