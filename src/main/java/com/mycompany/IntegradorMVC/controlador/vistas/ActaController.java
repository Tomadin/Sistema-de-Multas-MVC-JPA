package com.mycompany.IntegradorMVC.controlador.vistas;

import com.mycompany.IntegradorMVC.controlador.jpa.ActaDeConstatacionJpaController;
import com.mycompany.IntegradorMVC.controlador.jpa.AutoridadDeConstatacionJpaController;
import com.mycompany.IntegradorMVC.controlador.jpa.ConductorJpaController;
import com.mycompany.IntegradorMVC.controlador.jpa.InfraccionJpaController;
import com.mycompany.IntegradorMVC.controlador.jpa.LicenciaJpaController;
import com.mycompany.IntegradorMVC.controlador.jpa.OrganizacionEstatalJpaController;
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
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public final class ActaController implements ActionListener, PropertyChangeListener, ListSelectionListener {

    private final NuevaActa nuevaActa;
    private final InfraccionJpaController infraccionJpaController;
    private final OrganizacionEstatalJpaController organizacionJpaController;
    private final AutoridadDeConstatacionJpaController autoridadJpaController;
    private final ActaDeConstatacionJpaController actaJpaController;

    public ActaController(NuevaActa nuevaActa) {
        this.nuevaActa = nuevaActa;
        this.nuevaActa.setVisible(true);
        this.infraccionJpaController = new InfraccionJpaController();
        this.organizacionJpaController = new OrganizacionEstatalJpaController();
        this.autoridadJpaController = new AutoridadDeConstatacionJpaController();
        this.actaJpaController = new ActaDeConstatacionJpaController();

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

    private void crearNuevaActa() throws Exception {
        // OBTENCIÓN Y VALIDACIÓN DE DATOS SIMPLES
        String rutaStr = nuevaActa.rutaTextField.getText();
        String kilometroStr = nuevaActa.kilometroTextField2.getText();
        String colorStr = nuevaActa.colorTextField.getText();
        String anioStr = nuevaActa.anioTextField.getText();
        String numeroLicencia = nuevaActa.numeroLicenciaTextField.getText();
        String puntosStr = nuevaActa.puntosTextField.getText();
        String horaStr = nuevaActa.horaTextField.getText();
        String domicilio = nuevaActa.domicilioTextField.getText();
        String nombre = nuevaActa.nombreTextField.getText();
        String apellido = nuevaActa.apellidoTextField.getText();
        String dni = nuevaActa.dniTextField.getText();
        String genero = nuevaActa.generoTextField.getText();

        if (rutaStr.isEmpty() || anioStr.isEmpty() || numeroLicencia.isEmpty()) {
            JOptionPane.showMessageDialog(nuevaActa, "Campos Ruta, Año o ID Licencia obligatorios.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int anioVehiculo;
        int puntosLicencia;
        try {

            anioVehiculo = Integer.parseInt(anioStr);
            puntosLicencia = Integer.parseInt(puntosStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(nuevaActa, "El Año o Puntos deben ser números enteros.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // OBTENCIÓN DE OBJETOS SELECCIONADOS EN LISTAS
        OrganizacionEstatal organizacionSeleccionada = (OrganizacionEstatal) nuevaActa.organizacionJList.getSelectedValue();
        System.out.println("ACTA CONTROLER 165: " + organizacionSeleccionada.toString());
        if (organizacionSeleccionada == null) {
            JOptionPane.showMessageDialog(nuevaActa, "Debe seleccionar una Organización.", "Error de Selección", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ⭐ BUSCAR LAS ENTIDADES EXISTENTES POR ID para que estén en contexto
        AutoridadDeConstatacionJpaController autoridadJpaController = new AutoridadDeConstatacionJpaController();
        AutoridadDeConstatacion autoridadSeleccionada = (AutoridadDeConstatacion) nuevaActa.autoridadConstatacionJList.getSelectedValue();

        System.out.println(autoridadJpaController.find(autoridadSeleccionada.getDni()));
        System.out.println("LINEA 146: " + autoridadSeleccionada.toString());

        if (autoridadSeleccionada == null) {
            JOptionPane.showMessageDialog(nuevaActa, "Debe seleccionar una Autoridad.", "Error de Selección", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tipoRutaSeleccionado = (String) nuevaActa.tipoRutaJComboBox1.getSelectedItem();
        if (tipoRutaSeleccionado == null) {
            JOptionPane.showMessageDialog(nuevaActa, "Debe seleccionar un Tipo de Ruta.", "Error de Selección", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Infraccion> infraccionesSeleccionadas = nuevaActa.infraccionJList.getSelectedValuesList();

        for (Infraccion infraccion : infraccionesSeleccionadas) {
            System.out.println(infraccion.toString());

            for (TipoDeInfraccion tipo : infraccion.getInfraccionNomenclanda()) {
                System.out.println("  - Tipo: " + tipo.toString());
            }
        }

        if (infraccionesSeleccionadas.isEmpty()) {
            JOptionPane.showMessageDialog(nuevaActa, "Debe seleccionar al menos una Infracción.", "Error de Selección", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // CONSTRUCCIÓN DE OBJETOS ANIDADOS
        Vehiculo vehiculo = new Vehiculo(
                colorStr,
                nuevaActa.dominioTextField.getText(),
                anioVehiculo,
                new Marca(nuevaActa.marcaTextField.getText(), new Modelo(nuevaActa.modeloTextField.getText()))
        );

        // RUTA
        Ruta ruta = new Ruta(
                rutaStr,
                kilometroStr,
                new TipoRuta("Buen estado", tipoRutaSeleccionado)
        );

        // ⭐ CREAR CONDUCTOR (NO guardar aún)
        // ⭐ BUSCAR O CREAR CONDUCTOR
        ConductorJpaController conductorJpaController = new ConductorJpaController();
        Conductor conductor = conductorJpaController.find(Integer.valueOf(dni));

        if (conductor == null) {
            // El conductor no existe, crear uno nuevo
            conductor = new Conductor(
                    domicilio, nombre, apellido,
                    Integer.parseInt(dni), genero
            );
        } else {
            // El conductor existe, actualizar sus datos si es necesario
            conductor.setDomicilio(domicilio);
            conductor.setNombre(nombre);
            conductor.setApellido(apellido);
            conductor.setGenero(genero);
        }

        // ⭐ BUSCAR O CREAR LICENCIA
        LicenciaJpaController licenciaJpaController = new LicenciaJpaController();
        Licencia licencia = licenciaJpaController.find(Integer.parseInt(numeroLicencia));

        if (licencia == null) {
            // La licencia no existe, crear una nueva
            licencia = new Licencia(
                    Integer.parseInt(numeroLicencia),
                    nuevaActa.fechaVTOJDateChooser.getDate(),
                    puntosLicencia
            );
            licencia.setConductor(conductor);
        } else {
            // La licencia existe, verificar que pertenezca al conductor correcto
            if (licencia.getConductor() == null
                    || licencia.getConductor().getDni() != conductor.getDni()) {
                licencia.setConductor(conductor);
            }
        }
        // INFRACCION
        Infraccion infraccionBase = (Infraccion) nuevaActa.infraccionJList.getSelectedValue();

        double monto = infraccionBase.getImporteInfraccion();
        
        
        // CONSTRUCCIÓN FINAL DEL ACTADECONSTATACION
        ActaDeConstatacion nuevaActaConstatacion = new ActaDeConstatacion(
                nuevaActa.fechaConstatacionJDateChooser.getDate(),
                nuevaActa.fechaVTOActaConstatacionJDateChooser.getDate(),
                LocalDateTime.now(),
                nuevaActa.lugarTextField.getText(),
                nuevaActa.observacionesJTextArea.getText(),
                organizacionSeleccionada,
                vehiculo,
                new EstadoDelActa("ACTIVO", "Acta generada"), // Estado inicial
                autoridadSeleccionada,
                licencia,
                ruta
        );

        nuevaActaConstatacion.setAutoridadDeConstatacion(autoridadSeleccionada);

        // ⭐ AGREGAR TODAS LAS INFRACCIONES
        for (Infraccion infraccion : infraccionesSeleccionadas) {
            nuevaActaConstatacion.addInfraccion(infraccion);
        }
        nuevaActaConstatacion.addInfraccion(infraccionBase);

        if (autoridadSeleccionada != null) {
            AutoridadDeConstatacion autoridadEnBD = autoridadJpaController.find(autoridadSeleccionada.getDni());

            if (autoridadEnBD == null) {
                // Opción 1: Persistir la autoridad si no existía
                autoridadJpaController.create(autoridadSeleccionada);
                autoridadEnBD = autoridadSeleccionada;
            }

            nuevaActaConstatacion.setAutoridadDeConstatacion(autoridadEnBD);
        } else {
            JOptionPane.showMessageDialog(nuevaActa, "Debe seleccionar una Autoridad.", "Error de Selección", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // LLAMADA AL JPA PARA PERSISTENCIA
        actaJpaController.create(nuevaActaConstatacion);
        JOptionPane.showMessageDialog(nuevaActa, "Acta creada exitosamente. Importe final: $" + monto, "Éxito", JOptionPane.INFORMATION_MESSAGE);
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

            // Manejo de ComboBoxes (JComboBox)
        } 
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object origen = evt.getSource();

        if (origen == nuevaActa.fechaConstatacionJDateChooser) {
            System.out.println("-> Fecha de Constatación cambiada.");
            // Lógica: Si la fecha es inválida o posterior a hoy, mostrar un error.

        } else if (origen == nuevaActa.fechaVTOActaConstatacionJDateChooser) {
            System.out.println("-> Fecha de Vencimiento de Acta cambiada.");
            // Lógica: La fecha de vencimiento debería calcularse automáticamente o validarse.

        } else if (origen == nuevaActa.fechaVTOJDateChooser) {
            System.out.println("-> Fecha de Vencimiento de Licencia cambiada.");
            // Lógica: Validar que la licencia no esté vencida.
        } else if (origen == nuevaActa.observacionesJTextArea) {
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

//        cargarInfracciones();
        System.out.println("=== INICIANDO CARGA DE LISTAS ===");
        try {
            // --- INFRACCIONES ---
            System.out.println("📋 Cargando infracciones...");
            List<Infraccion> infracciones = infraccionJpaController.findAll();
            System.out.println("✅ Infracciones encontradas: " + infracciones.size());
            DefaultListModel<Infraccion> modeloInfraccion = new DefaultListModel<>();
            for (Infraccion i : infracciones) {
                modeloInfraccion.addElement(i);
            }
            nuevaActa.infraccionJList.setModel(modeloInfraccion);
            System.out.println("✅ JList infracciones configurado con " + modeloInfraccion.size() + " elementos");

            // --- ORGANIZACIONES ---
            System.out.println("\n🏢 Cargando organizaciones...");
            List<OrganizacionEstatal> organizaciones = organizacionJpaController.findAll();
            System.out.println("✅ Organizaciones encontradas: " + organizaciones.size());

            DefaultListModel<OrganizacionEstatal> modeloOrganizacion = new DefaultListModel<>();
            for (OrganizacionEstatal org : organizaciones) {
                modeloOrganizacion.addElement(org);
            }
            nuevaActa.organizacionJList.setModel(modeloOrganizacion);
            System.out.println("✅ JList organizaciones configurado");
            // --- AUTORIDADES DE CONSTATACIÓN ---
            System.out.println("\n👮 Cargando autoridades...");
            List<AutoridadDeConstatacion> autoridades = autoridadJpaController.findAll();
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
