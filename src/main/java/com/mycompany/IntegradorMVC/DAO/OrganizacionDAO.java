
package com.mycompany.IntegradorMVC.DAO;

import com.mycompany.IntegradorMVC.modelo.OrganizacionEstatal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class OrganizacionDAO extends SQLQuery {
    
    public boolean crearOrganizacion(OrganizacionEstatal organizacion) throws SQLException{
        int result = 0;
        
        try {
            this.conectar();
            String query = "INSERT INTO Organizacion"
                + "(nombreOrganizacion, localidad) VALUES"
                + "(?,?)";
            
            this.consulta = this.conn.prepareStatement(query);
            
            consulta.setString(1, organizacion.getNombreOrganizacion());
            consulta.setString(2, organizacion.getLocalidad());
            
            result = consulta.executeUpdate();
            System.out.println(result);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrganizacionDAO.class.getName()).log(Level.SEVERE, "Problema creando Organización Estatal", ex);
        }
        
        return result>0;
    }
    
     public boolean actualizarOrganizacion(OrganizacionEstatal organizacion){
        int result = 0;
        
        try {
            this.conectar();
            
            String query = "UPDATE organizacion SET nombreOrganizacion=?, localidad=? WHERE id=?";

            this.consulta = this.conn.prepareStatement(query);
            
            consulta.setString(1, organizacion.getNombreOrganizacion());
            consulta.setString(2, organizacion.getLocalidad());
            
            consulta.setInt(3, organizacion.getId()); //no hay un id en OrganizacionEstatal
            
            result = consulta.executeUpdate();
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrganizacionDAO.class.getName()).log(Level.SEVERE, "Problema actualizando Autoridad de Constatación", ex);
        } finally {
            this.desconectar();
        }
        
        return result>0;
    }
     
     public boolean eliminarOrganizacion(OrganizacionEstatal organizacion){

        int result = 0;
        try {
            this.conectar();
            
            String query = "DELETE FROM Organizacion WHERE id=?";

            this.consulta = this.conn.prepareStatement(query);
            
            consulta.setInt(1, organizacion.getId());

            result = consulta.executeUpdate();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrganizacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar();
        }
        
        
        return result>0;
    }
    
    public ArrayList<OrganizacionEstatal> obtenerOrganizaciones(){
        
        ArrayList<OrganizacionEstatal> organizaciones = new ArrayList<>();
        ResultSet resultSet = null;
        
        try {
            this.conectar();
            
            String query = "SELECT * FROM Organizacion";

            this.consulta = this.conn.prepareStatement(query);

            resultSet = consulta.executeQuery();
            
            while (resultSet.next()) {
                OrganizacionEstatal organizacion = new OrganizacionEstatal(resultSet.getString(2), resultSet.getString(3));
                organizacion.setId(resultSet.getInt(1));
                organizaciones.add(organizacion);
            }
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrganizacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(resultSet);
        }
        
        return organizaciones;
    }
    
    public OrganizacionEstatal buscarOrganizacionPorId(int id){
        
        
        ResultSet resultSet = null;
        OrganizacionEstatal or = new OrganizacionEstatal();
        
        try {
            this.conectar();
            
            String query = "SELECT * FROM Organizacion WHERE id=?";
            

            this.consulta = this.conn.prepareStatement(query);
            consulta.setInt(1, id);
            
            resultSet = consulta.executeQuery();
            
            
            
            while (resultSet.next()) {
                OrganizacionEstatal autoridad = new OrganizacionEstatal(resultSet.getString(1), resultSet.getString(2));
                break;
            }
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrganizacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(resultSet);
        }
        
        return or;
    }
    
    
    
    
    public boolean organizacionExiste(OrganizacionEstatal organizacion){
        
        int result = 0;
        ResultSet resultSet = null;
        
        try {
            this.conectar();
            String query = "SELECT * FROM Organizacion WHERE id=?";

            this.consulta = this.conn.prepareStatement(query);
            consulta.setLong(1, organizacion.getId());
            resultSet = consulta.executeQuery();
            
            int count = 0;
            while (resultSet.next()) {
                count++;
            }
            
            return count > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrganizacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result>0;
    }
}
