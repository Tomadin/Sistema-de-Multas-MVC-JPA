
package com.mycompany.IntegradorMVC.DAO;

import com.mycompany.IntegradorMVC.modelo.AutoridadDeConstatacion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class AutoridadDAO extends SQLQuery {
    @PersistenceContext
    EntityManager em;
    public boolean crearAutoridad(AutoridadDeConstatacion autoridad) throws SQLException{
        int result = 0;
        
        try {
            this.conectar();
            String query = "INSERT INTO Autoridad"
                + "(dni, nombre, apellido, genero, idPlaca, idLegajo) VALUES"
                + "(?,?,?,?,?,?)";

            this.consulta = this.conn.prepareStatement(query);
            
            consulta.setLong(1, autoridad.getDni());
            consulta.setString(2, autoridad.getNombre());
            consulta.setString(3, autoridad.getApellido());
            
            
            consulta.setString(4, autoridad.getGenero());
            
            consulta.setInt(5, autoridad.getIdPlaca());
            consulta.setInt(6, autoridad.getIdLegajo());
  
            result = consulta.executeUpdate();
            System.out.println(result);
        } catch (ClassNotFoundException | SQLException ex) {
            
            Logger.getLogger(AutoridadDAO.class.getName()).log(Level.SEVERE, "Problema creando Autoridad De Constatación", ex);
        }
        
        return result>0;
    }
    
     public boolean actualizarAutoridad(AutoridadDeConstatacion autoridad){

        int result = 0;
        
        try {
            this.conectar();
            
            String query = "UPDATE Autoridad SET nombre=?, apellido=?, genero=?, "
                    + "idPlaca=?, idLegajo=? WHERE dni=?";

            this.consulta = this.conn.prepareStatement(query);
            
            consulta.setString(1, autoridad.getNombre());
            consulta.setString(2, autoridad.getApellido());
            
            
            consulta.setString(3, autoridad.getGenero());
            
            consulta.setInt(4, autoridad.getIdPlaca());
            consulta.setInt(5, autoridad.getIdLegajo());
            
            consulta.setInt(6, autoridad.getDni());

            result = consulta.executeUpdate();
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AutoridadDeConstatacion.class.getName()).log(Level.SEVERE, "Problema actualizando Autoridad de Constatación", ex);
        } finally {
            this.desconectar();
        }
        
        return result>0;
    }
     
     public boolean eliminarAutoridad(AutoridadDeConstatacion autoridad){

        int result = 0;
        try {
            this.conectar();
            
            String query = "DELETE FROM Autoridad WHERE dni=?";

            this.consulta = this.conn.prepareStatement(query);
            
            consulta.setInt(1, autoridad.getDni());

            result = consulta.executeUpdate();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AutoridadDeConstatacion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar();
        }
        
        
        return result>0;
    }
    
    public ArrayList<AutoridadDeConstatacion> buscarAutoridades(){
        
        ArrayList<AutoridadDeConstatacion> autoridades = new ArrayList<>();
        ResultSet resultSet = null;
        
        try {
            this.conectar();
            
            String query = "SELECT * FROM Autoridad";

            this.consulta = this.conn.prepareStatement(query);

            resultSet = consulta.executeQuery();
            
            while (resultSet.next()) {
                AutoridadDeConstatacion autoridad = new AutoridadDeConstatacion();
                
                autoridad.setDni(resultSet.getInt(1));
                autoridad.setNombre(resultSet.getString(2));
                autoridad.setApellido(resultSet.getString(3));
                autoridad.setGenero(resultSet.getString(4));
                autoridad.setIdPlaca(resultSet.getInt(5));
                autoridad.setIdLegajo(resultSet.getInt(6));

                
                
                autoridades.add(autoridad);

            }
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AutoridadDeConstatacion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(resultSet);
        }
        
        return autoridades;
    }
    
    public AutoridadDeConstatacion buscarAutoridadPorDni(int dni){
        
        
        ResultSet resultSet = null;
        AutoridadDeConstatacion au = new AutoridadDeConstatacion();
        
        try {
            this.conectar();
            
            String query = "SELECT * FROM Autoridad WHERE dni=?";
            

            this.consulta = this.conn.prepareStatement(query);
            consulta.setInt(1, dni);
            
            resultSet = consulta.executeQuery();
            
            
            
            while (resultSet.next()) {
                AutoridadDeConstatacion autoridad = new AutoridadDeConstatacion();
                
                au.setDni(resultSet.getInt(1));
                au.setNombre(resultSet.getString(2));
                au.setApellido(resultSet.getString(3));
                au.setGenero(resultSet.getString(4));
                au.setIdPlaca(resultSet.getInt(5));
                au.setIdLegajo(resultSet.getInt(6));
                break;
            }
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AutoridadDeConstatacion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(resultSet);
        }
        
        return au;
    }
    
    
    
    
    public boolean autoridadExiste(AutoridadDeConstatacion autoridad){
        
        int result = 0;
        ResultSet resultSet = null;
        
        try {
            this.conectar();
            String query = "SELECT * FROM Alumno WHERE dni=?";

            this.consulta = this.conn.prepareStatement(query);
            consulta.setLong(1, autoridad.getDni());
            resultSet = consulta.executeQuery();
            
            int count = 0;
            while (resultSet.next()) {
                count++;
            }
            
            return count > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AutoridadDeConstatacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result>0;
    }
}
