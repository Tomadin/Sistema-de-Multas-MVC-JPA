
package com.mycompany.IntegradorMVC.controlador;

import com.mycompany.IntegradorMVC.DAO.AutoridadDAO;
import com.mycompany.IntegradorMVC.modelo.AutoridadDeConstatacion;
import com.mycompany.IntegradorMVC.vista.NuevaAutoridad;
import com.mycompany.IntegradorMVC.vista.VistaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class NuevaAurodidadController implements ActionListener{
   private final NuevaAutoridad nuevaAutoridad;
   private final AutoridadDAO autoridadDAO;

    public NuevaAurodidadController(NuevaAutoridad nuevaAutoridad) {
        this.nuevaAutoridad = nuevaAutoridad;
        this.autoridadDAO = new AutoridadDAO();
        nuevaAutoridad.setVisible(true);
        nuevaAutoridad.backBtn.addActionListener(this);
        nuevaAutoridad.crearAutoridadBtn.addActionListener(this);
        
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if(o.equals(nuevaAutoridad.backBtn)){
            VistaPrincipal vistaPrincipal = new VistaPrincipal();
            VistaPrincipalController controladorVistaPrincipal = new VistaPrincipalController(vistaPrincipal);
            nuevaAutoridad.setVisible(false);
        }
        if(o.equals(nuevaAutoridad.crearAutoridadBtn)){
            crearAutoridad();
            
        }
        
    }
    
    private void crearAutoridad(){
        int opcion = nuevaAutoridad.generoJComboBox.getSelectedIndex();
            String genero ="";
            try {
            switch (opcion) {
                case 0:
                    throw new AssertionError();

                case 1:
                    genero = "hombre";
                    break;
                case 2:
                    genero = "mujer";
                    break;
                default:
                    throw new AssertionError();
            }
        } catch (Exception e) {
                System.out.println("DEBE SELECCIONAR UN GENERO CORRECTO.");
                System.out.println(e);
        }
            

            AutoridadDeConstatacion autoridad = new AutoridadDeConstatacion(      
                    Integer.parseInt(nuevaAutoridad.placaTextField.getText()),
                    Integer.parseInt(nuevaAutoridad.legajoTextField.getText()),
                    nuevaAutoridad.nombreTextField.getText(),
                    nuevaAutoridad.apellidoTextField.getText(),
                    Integer.parseInt(nuevaAutoridad.dniTextField.getText()),
                    genero
                    );
            try {
                autoridadDAO.crearAutoridad(autoridad);
                
                 JOptionPane.showMessageDialog(nuevaAutoridad, "Autoridad creada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (SQLException ex) {
                Logger.getLogger(NuevaAurodidadController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
   
   
}
