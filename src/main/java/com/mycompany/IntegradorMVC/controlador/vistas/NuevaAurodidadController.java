package com.mycompany.IntegradorMVC.controlador.vistas;

import com.mycompany.IntegradorMVC.controlador.jpa.AutoridadDeConstatacionJpaController;
import com.mycompany.IntegradorMVC.modelo.AutoridadDeConstatacion;
import com.mycompany.IntegradorMVC.vista.NuevaAutoridad;
import com.mycompany.IntegradorMVC.vista.VistaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class NuevaAurodidadController implements ActionListener {
//   private final NuevaAutoridad nuevaAutoridad;
//   private final AutoridadDAO autoridadDAO;

    private final NuevaAutoridad nuevaAutoridad;
    private final AutoridadDeConstatacionJpaController autoridadJpaController;

    public NuevaAurodidadController(NuevaAutoridad nuevaAutoridad) {
        this.nuevaAutoridad = nuevaAutoridad;
        //this.autoridadDAO = new AutoridadDAO();
        this.autoridadJpaController = new AutoridadDeConstatacionJpaController();
        nuevaAutoridad.setVisible(true);
        nuevaAutoridad.backBtn.addActionListener(this);
        nuevaAutoridad.crearAutoridadBtn.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o.equals(nuevaAutoridad.backBtn)) {
            VistaPrincipal vistaPrincipal = new VistaPrincipal();
            VistaPrincipalController controladorVistaPrincipal = new VistaPrincipalController(vistaPrincipal);
            nuevaAutoridad.setVisible(false);
        }
        if (o.equals(nuevaAutoridad.crearAutoridadBtn)) {
            try {
                crearAutoridad();
            } catch (Exception ex) {
                Logger.getLogger(NuevaAurodidadController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void crearAutoridad() throws Exception {
        int opcion = nuevaAutoridad.generoJComboBox.getSelectedIndex();
        String genero = "";
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
                    JOptionPane.showMessageDialog(nuevaAutoridad,
                            "Debe seleccionar un género válido.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
            }

            AutoridadDeConstatacion autoridad = new AutoridadDeConstatacion(
                    Integer.parseInt(nuevaAutoridad.placaTextField.getText()),
                    Integer.parseInt(nuevaAutoridad.legajoTextField.getText()),
                    nuevaAutoridad.nombreTextField.getText(),
                    nuevaAutoridad.apellidoTextField.getText(),
                    Integer.parseInt(nuevaAutoridad.dniTextField.getText()),
                    genero
            );
            autoridadJpaController.create(autoridad);

            JOptionPane.showMessageDialog(nuevaAutoridad,
                    "Autoridad creada exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(NuevaAurodidadController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
