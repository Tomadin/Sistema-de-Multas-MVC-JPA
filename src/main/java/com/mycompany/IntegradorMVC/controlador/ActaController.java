package com.mycompany.IntegradorMVC.controlador;

import com.mycompany.IntegradorMVC.DAO.ActaDeConstatacionDAO;
import com.mycompany.IntegradorMVC.DAO.AutoridadDAO;
import com.mycompany.IntegradorMVC.DAO.InfraccionDAO;
import com.mycompany.IntegradorMVC.DAO.OrganizacionDAO;
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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public final class ActaController implements ActionListener, PropertyChangeListener, ListSelectionListener { 

    private final NuevaActa nuevaActa;
    private final InfraccionDAO infraccionDAO;
    private final OrganizacionDAO organizacionDAO;
    private final AutoridadDAO autoridadDAO;
    private final ActaDeConstatacionDAO actaDAO;

    public ActaController(NuevaActa nuevaActa) {
        this.nuevaActa = nuevaActa;
        this.nuevaActa.setVisible(true);
        this.infraccionDAO = new InfraccionDAO();
        this.organizacionDAO = new OrganizacionDAO();
        this.autoridadDAO = new AutoridadDAO();
        this.actaDAO = new ActaDeConstatacionDAO();

        cargarListasSeleccion();

        //JComboBox
        nuevaActa.importeJComboBox.addActionListener(this);
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
        nuevaActa.idLicenciaTextField.addActionListener(this);
        nuevaActa.lugarTextField.addActionListener(this);
        nuevaActa.puntosTextField.addActionListener(this);
        nuevaActa.horaTextField.addActionListener(this);
    }

    private void crearNuevaActa() {
        // OBTENCIÓN Y VALIDACIÓN DE DATOS SIMPLES
        String rutaStr = nuevaActa.rutaTextField.getText();
        String kilometroStr = nuevaActa.kilometroTextField2.getText();
        String colorStr = nuevaActa.colorTextField.getText();
        String anioStr = nuevaActa.anioTextField.getText();
        String idLicenciaStr = nuevaActa.idLicenciaTextField.getText();
        String puntosStr = nuevaActa.puntosTextField.getText();
        String horaStr = nuevaActa.horaTextField.getText();
        String domicilio = nuevaActa.domicilioTextField.getText();
        String nombre = nuevaActa.nombreTextField.getText();
        String apellido = nuevaActa.apellidoTextField.getText();
        String dni = nuevaActa.dniTextField.getText();
        String genero = nuevaActa.generoTextField.getText();

        if (rutaStr.isEmpty() || anioStr.isEmpty() || idLicenciaStr.isEmpty()) {
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

        AutoridadDeConstatacion autoridadSeleccionada = (AutoridadDeConstatacion) nuevaActa.autoridadConstatacionJList.getSelectedValue();
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

        // LICENCIA
        Licencia licencia = new Licencia(
                Integer.parseInt(idLicenciaStr),
                nuevaActa.fechaVTOJDateChooser.getDate(), // java.util.Date
                puntosLicencia
        );
        licencia.setConductor(new Conductor(domicilio, nombre, apellido, Integer.parseInt(dni), genero));

        // INFRACCION
        Infraccion infraccionBase = (Infraccion) nuevaActa.infraccionJList.getSelectedValue();

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

        nuevaActaConstatacion.addInfraccion(infraccionBase);

        // LLAMADA AL DAO PARA PERSISTENCIA
        actaDAO.guardarActa(nuevaActaConstatacion);
        JOptionPane.showMessageDialog(nuevaActa, "Acta creada exitosamente. Importe final: $" + 100, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        // Manejo de Botones
        if (o instanceof JButton) {

            if (o == nuevaActa.crearBtn) {
                System.out.println("-> Botón Crear presionado. Iniciando guardado.");
                crearNuevaActa();
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
        } else if (o instanceof JComboBox) {
            if (o == nuevaActa.importeJComboBox) {
                System.out.println("-> Importe seleccionado cambiado.");
            } else if (o == nuevaActa.tipoRutaJComboBox1) {
                System.out.println("-> Tipo de Ruta seleccionado cambiado.");
            }
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
        }
    }

    public void cargarListasSeleccion() {

        // --- INFRACCIONES ---
        List<Infraccion> infracciones = infraccionDAO.obtenerInfracciones();
        DefaultListModel<Infraccion> modeloInfraccion = new DefaultListModel<>();
        for (Infraccion i : infracciones) {
            modeloInfraccion.addElement(i);
        }
        nuevaActa.infraccionJList.setModel(modeloInfraccion);

        // --- ORGANIZACIONES ---
        List<OrganizacionEstatal> organizaciones = organizacionDAO.obtenerOrganizaciones();
        DefaultListModel<OrganizacionEstatal> modeloOrganizacion = new DefaultListModel<>();
        for (OrganizacionEstatal org : organizaciones) {
            modeloOrganizacion.addElement(org);
        }
        nuevaActa.organizacionJList.setModel(modeloOrganizacion);

        // --- AUTORIDADES DE CONSTATACIÓN ---
        List<AutoridadDeConstatacion> autoridades = autoridadDAO.buscarAutoridades();
        DefaultListModel<AutoridadDeConstatacion> modeloAutoridad = new DefaultListModel<>();
        for (AutoridadDeConstatacion a : autoridades) {
            modeloAutoridad.addElement(a);
        }
        nuevaActa.autoridadConstatacionJList.setModel(modeloAutoridad);
    }

}
