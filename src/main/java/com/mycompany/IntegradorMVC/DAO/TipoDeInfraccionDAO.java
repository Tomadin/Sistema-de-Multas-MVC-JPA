/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.IntegradorMVC.DAO;

import com.mycompany.IntegradorMVC.modelo.TipoDeInfraccion;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TipoDeInfraccionDAO extends SQLQuery {

    public boolean crearTipoDeInfraccion(TipoDeInfraccion tipo) {
        try {
            this.conectar();
            String query = "INSERT INTO TipoDeInfraccion (id_infrac, descripcionInfraccion, tipoGravedad, importeAsignadoInfraccion, porcentajeDescuento) VALUES (?, ?, ?, ?, ?)";
            this.consulta = this.conn.prepareStatement(query);
            consulta.setInt(1, tipo.getId_infrac());
            consulta.setString(2, tipo.getDescripcionInfraccion());
            consulta.setString(3, tipo.getTipoGravedad());
            consulta.setDouble(4, tipo.getImporteAsignadoInfraccion());
            consulta.setDouble(5, tipo.getPorcentajeDescuento());

            int result = consulta.executeUpdate();
            return result > 0;

        } catch (Exception ex) {
            Logger.getLogger(TipoDeInfraccionDAO.class.getName()).log(Level.SEVERE, "Error creando TipoDeInfraccion", ex);
            return false;
        }
    }

    public boolean existeTipoDeInfraccion(int id) {
        try {
            this.conectar();
            String query = "SELECT COUNT(*) FROM TipoDeInfraccion WHERE id_infrac = ?";
            this.consulta = this.conn.prepareStatement(query);
            consulta.setInt(1, id);

            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;

        } catch (Exception ex) {
            Logger.getLogger(TipoDeInfraccionDAO.class.getName()).log(Level.SEVERE, "Error verificando TipoDeInfraccion", ex);
            return false;
        }
    }

}
