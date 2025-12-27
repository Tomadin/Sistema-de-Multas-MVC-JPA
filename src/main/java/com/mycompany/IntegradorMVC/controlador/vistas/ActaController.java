package com.mycompany.IntegradorMVC.controlador.vistas;

import com.mycompany.IntegradorMVC.modelo.ActaDeConstatacion;
import com.mycompany.IntegradorMVC.modelo.AutoridadDeConstatacion;
import com.mycompany.IntegradorMVC.modelo.Conductor;
import com.mycompany.IntegradorMVC.modelo.ControladoraLogica;
import com.mycompany.IntegradorMVC.modelo.EstadoDelActa;
import com.mycompany.IntegradorMVC.modelo.Infraccion;
import com.mycompany.IntegradorMVC.modelo.Licencia;
import com.mycompany.IntegradorMVC.modelo.Marca;
import com.mycompany.IntegradorMVC.modelo.Modelo;
import com.mycompany.IntegradorMVC.modelo.OrganizacionEstatal;
import com.mycompany.IntegradorMVC.modelo.Ruta;
import com.mycompany.IntegradorMVC.modelo.TipoRuta;
import com.mycompany.IntegradorMVC.modelo.Vehiculo;
import com.mycompany.IntegradorMVC.vista.NuevaActa;
import com.mycompany.IntegradorMVC.vista.VistaPrincipal;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public final class ActaController implements ActionListener, PropertyChangeListener, ListSelectionListener {

    private final NuevaActa nuevaActa;
    private final ControladoraLogica controladoraLogica;
   
    public ActaController(NuevaActa nuevaActa) {
        this.nuevaActa = nuevaActa;
        this.nuevaActa.setVisible(true);
        this.controladoraLogica = new ControladoraLogica();

        //JComboBox
        nuevaActa.tipoRutaJComboBox1.addActionListener(this);
        nuevaActa.autoridadConstatacionJList.addListSelectionListener(this);

        //JList 
        nuevaActa.infraccionJList.addListSelectionListener(this);
        nuevaActa.organizacionJList.addListSelectionListener(this);

        //DateChooser
        nuevaActa.fechaVTOJDateChooser.addPropertyChangeListener(this);
        nuevaActa.fechaConstatacionJDateChooser.addPropertyChangeListener(this);
        nuevaActa.fechaVTOActaConstatacionJDateChooser.addPropertyChangeListener(this);

        //Text Area
        nuevaActa.observacionesJTextArea.addPropertyChangeListener(this);

        //Button
        nuevaActa.backBtn.addActionListener(this);
        nuevaActa.crearBtn.addActionListener(this);

        //TextField
        nuevaActa.descripcionTextField.addActionListener(this);
        nuevaActa.rutaTextField.addActionListener(this);
        nuevaActa.kilometroTextField2.addActionListener(this);
        nuevaActa.modeloTextField.addActionListener(this);
        nuevaActa.marcaTextField.addActionListener(this);
        nuevaActa.dominioTextField.addActionListener(this);
        nuevaActa.colorTextField.addActionListener(this);
        nuevaActa.anioTextField.addActionListener(this);
        nuevaActa.nombreTextField.addActionListener(this);
        nuevaActa.apellidoTextField.addActionListener(this);
        nuevaActa.dniTextField.addActionListener(this);
        nuevaActa.generoTextField.addActionListener(this);
        nuevaActa.domicilioTextField.addActionListener(this);
        nuevaActa.numeroLicenciaTextField.addActionListener(this);
        nuevaActa.lugarTextField.addActionListener(this);
        nuevaActa.puntosTextField.addActionListener(this);
        nuevaActa.horaTextField.addActionListener(this);
        SwingUtilities.invokeLater(() -> {
            cargarListasSeleccion();
        });
    }

    private void crearNuevaActa() {
        try {
            String rutaStr = nuevaActa.rutaTextField.getText();
            String kilometroStr = nuevaActa.kilometroTextField2.getText();
            String colorStr = nuevaActa.colorTextField.getText();
            String anioStr = nuevaActa.anioTextField.getText();
            String numeroLicencia = nuevaActa.numeroLicenciaTextField.getText();
            String puntosStr = nuevaActa.puntosTextField.getText();
            String domicilio = nuevaActa.domicilioTextField.getText();
            String nombre = nuevaActa.nombreTextField.getText();
            String apellido = nuevaActa.apellidoTextField.getText();
            String dni = nuevaActa.dniTextField.getText();
            String genero = nuevaActa.generoTextField.getText();

            if (rutaStr.isEmpty() || anioStr.isEmpty() || numeroLicencia.isEmpty()) {
                JOptionPane.showMessageDialog(nuevaActa, 
                    "Campos Ruta, Año o ID Licencia obligatorios.", 
                    "Error de Datos", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            int anioVehiculo;
            int puntosLicencia;
            int dniInt;
            int numeroLicenciaInt;
            
            try {
                anioVehiculo = Integer.parseInt(anioStr);
                puntosLicencia = Integer.parseInt(puntosStr);
                dniInt = Integer.parseInt(dni);
                numeroLicenciaInt = Integer.parseInt(numeroLicencia);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(nuevaActa, 
                    "El Año, Puntos o DNI deben ser números enteros.", 
                    "Error de Formato", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            OrganizacionEstatal organizacionSeleccionada = 
                (OrganizacionEstatal) nuevaActa.organizacionJList.getSelectedValue();
            
            if (organizacionSeleccionada == null) {
                JOptionPane.showMessageDialog(nuevaActa, 
                    "Debe seleccionar una Organización.", 
                    "Error de Selección", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            AutoridadDeConstatacion autoridadSeleccionada = 
                (AutoridadDeConstatacion) nuevaActa.autoridadConstatacionJList.getSelectedValue();
            
            if (autoridadSeleccionada == null) {
                JOptionPane.showMessageDialog(nuevaActa, 
                    "Debe seleccionar una Autoridad.", 
                    "Error de Selección", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            String tipoRutaSeleccionado = 
                (String) nuevaActa.tipoRutaJComboBox1.getSelectedItem();
            
            if (tipoRutaSeleccionado == null) {
                JOptionPane.showMessageDialog(nuevaActa, 
                    "Debe seleccionar un Tipo de Ruta.", 
                    "Error de Selección", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Infraccion> infraccionesSeleccionadas = 
                nuevaActa.infraccionJList.getSelectedValuesList();
            
            if (infraccionesSeleccionadas.isEmpty()) {
                JOptionPane.showMessageDialog(nuevaActa, 
                    "Debe seleccionar al menos una Infracción.", 
                    "Error de Selección", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ==================== CONSTRUCCIÓN DE OBJETOS ====================
            
            // Vehículo
            Vehiculo vehiculo = new Vehiculo(
                colorStr,
                nuevaActa.dominioTextField.getText(),
                anioVehiculo,
                new Marca(
                    nuevaActa.marcaTextField.getText(), 
                    new Modelo(nuevaActa.modeloTextField.getText())
                )
            );

            // Ruta
            Ruta ruta = new Ruta(
                rutaStr,
                kilometroStr,
                new TipoRuta("Buen estado", tipoRutaSeleccionado)
            );

            // Conductor
            Conductor conductor = new Conductor(
                domicilio, nombre, apellido, dniInt, genero
            );

            // Licencia
            Licencia licencia = new Licencia(
                numeroLicenciaInt,
                nuevaActa.fechaVTOJDateChooser.getDate(),
                puntosLicencia
            );
            licencia.setConductor(conductor);

            // Acta de Constatación
            ActaDeConstatacion nuevaActaConstatacion = new ActaDeConstatacion(
                nuevaActa.fechaConstatacionJDateChooser.getDate(),
                nuevaActa.fechaVTOActaConstatacionJDateChooser.getDate(),
                LocalDateTime.now(),
                nuevaActa.lugarTextField.getText(),
                nuevaActa.observacionesJTextArea.getText(),
                organizacionSeleccionada,
                vehiculo,
                new EstadoDelActa("ACTIVO", "Acta generada"),
                autoridadSeleccionada,
                licencia,
                ruta
            );

            // Agregar infracciones
            for (Infraccion infraccion : infraccionesSeleccionadas) {
                nuevaActaConstatacion.addInfraccion(infraccion);
            }

            
            controladoraLogica.crearActa(nuevaActaConstatacion);

            double montoTotal = controladoraLogica.calcularTotalInfraccionesActa(nuevaActaConstatacion);
            
            JOptionPane.showMessageDialog(nuevaActa, 
                String.format("✅ Acta creada exitosamente.\nImporte total: $%.2f", montoTotal), 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            

        } catch (Exception ex) {
            System.err.println("❌ Error al crear acta: " + ex.getMessage());
            ex.printStackTrace();
            
            JOptionPane.showMessageDialog(nuevaActa,
                "Error al crear acta: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        // Manejo de Botones
        if (o instanceof JButton) {

            if (o == nuevaActa.crearBtn) {
                System.out.println("-> Botón Crear presionado. Iniciando guardado.");
                try {
                    crearNuevaActa();
                } catch (Exception ex) {
                    Logger.getLogger(ActaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (o == nuevaActa.backBtn) {
                VistaPrincipal vistaPrincipal = new VistaPrincipal();
                VistaPrincipalController controladorVistaPrincipal = new VistaPrincipalController(vistaPrincipal);
                nuevaActa.setVisible(false);

            }

            // Logs porque tengo que hacerme ver el autismo
        } else if (o instanceof JTextField) {
            if (o == nuevaActa.descripcionTextField) {
                System.out.println("-> Texto de Descripción finalizado (ENTER).");
            } else if (o == nuevaActa.rutaTextField) {
                System.out.println("-> Texto de Ruta finalizado.");
            } else if (o == nuevaActa.kilometroTextField2) {
                System.out.println("-> Texto de Kilómetro finalizado.");
            } else if (o == nuevaActa.modeloTextField) {
                System.out.println("-> Texto de Modelo finalizado.");
            } else if (o == nuevaActa.marcaTextField) {
                System.out.println("-> Texto de Marca finalizado.");
            }
        } 
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object origen = evt.getSource();

        if (origen == nuevaActa.fechaConstatacionJDateChooser) {
            System.out.println("-> Fecha de Constatación cambiada.");
        } 
        
        if (origen == nuevaActa.fechaVTOActaConstatacionJDateChooser) {
            System.out.println("-> Fecha de Vencimiento de Acta cambiada.");
        } 
        
        if (origen == nuevaActa.fechaVTOJDateChooser) {
            System.out.println("-> Fecha de Vencimiento de Licencia cambiada.");
        } 
        
        if (origen == nuevaActa.observacionesJTextArea) {
            System.out.println("-> Texto de Observaciones modificado.");
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }

        JList<?> origenLista = (JList<?>) e.getSource();

        if (origenLista == nuevaActa.infraccionJList) {
            System.out.println("-> Selección de Infracciones cambiada.");

        } else if (origenLista == nuevaActa.organizacionJList) {
            System.out.println("-> Selección de Organización cambiada.");

        } else if (origenLista == nuevaActa.autoridadConstatacionJList) {
            System.out.println("-> Selección de Autoridad de Constatación cambiada.");
            System.out.println(nuevaActa.autoridadConstatacionJList.getSelectedValue().toString());
        }
    }

    public void cargarListasSeleccion() {
        System.out.println("=== INICIANDO CARGA DE LISTAS ===");
        try {
            // --- INFRACCIONES ---
            System.out.println("📋 Cargando infracciones...");
            List<Infraccion> infracciones = controladoraLogica.obtenerTodasLasInfracciones();
            System.out.println("✅ Infracciones encontradas: " + infracciones.size());
            DefaultListModel<Infraccion> modeloInfraccion = new DefaultListModel<>();
            for (Infraccion i : infracciones) {
                modeloInfraccion.addElement(i);
            }
            nuevaActa.infraccionJList.setModel(modeloInfraccion);
            System.out.println("✅ JList infracciones configurado con " + modeloInfraccion.size() + " elementos");

            // --- ORGANIZACIONES ---
            System.out.println("\n🏢 Cargando organizaciones...");
            List<OrganizacionEstatal> organizaciones = controladoraLogica.obtenerTodasLasOrganizaciones();
            System.out.println("✅ Organizaciones encontradas: " + organizaciones.size());

            DefaultListModel<OrganizacionEstatal> modeloOrganizacion = new DefaultListModel<>();
            for (OrganizacionEstatal org : organizaciones) {
                modeloOrganizacion.addElement(org);
            }
            nuevaActa.organizacionJList.setModel(modeloOrganizacion);
            System.out.println("✅ JList organizaciones configurado");
            // --- AUTORIDADES DE CONSTATACIÓN ---
            System.out.println("\n👮 Cargando autoridades...");
            List<AutoridadDeConstatacion> autoridades = controladoraLogica.obtenerTodasLasAutoridades();
            System.out.println("✅ Autoridades encontradas: " + autoridades.size());

            DefaultListModel<AutoridadDeConstatacion> modeloAutoridad = new DefaultListModel<>();
            for (AutoridadDeConstatacion a : autoridades) {
                modeloAutoridad.addElement(a);
            }
            System.out.println("✅ JList autoridades configurado");

            nuevaActa.autoridadConstatacionJList.setModel(modeloAutoridad);
            System.out.println("\n=== CARGA COMPLETADA ===\n");
        } catch (Exception ex) {
            System.err.println("❌ ERROR en cargarListasSeleccion: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(nuevaActa,
                    "Error cargando datos: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    
}
