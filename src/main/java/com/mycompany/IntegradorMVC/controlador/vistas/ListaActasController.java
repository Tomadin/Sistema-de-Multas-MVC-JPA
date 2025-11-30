package com.mycompany.IntegradorMVC.controlador.vistas;

import com.mycompany.IntegradorMVC.controlador.jpa.ActaDeConstatacionJpaController;
import com.mycompany.IntegradorMVC.modelo.ActaDeConstatacion;
import com.mycompany.IntegradorMVC.vista.ListaActas;
import com.mycompany.IntegradorMVC.vista.VistaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ListaActasController implements ActionListener {

    public ListaActas listaActas;
    public final ActaDeConstatacionJpaController actaJpaController;
    //public final ActaDeConstatacionDAO actaDAO;

    public ListaActasController(ListaActas vista) throws SQLException {
        this.listaActas = vista;
        this.listaActas.setVisible(true);
        this.actaJpaController = new ActaDeConstatacionJpaController();
        llenarTabla();
        listaActas.backBtn.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o.equals(listaActas.backBtn)) {
            VistaPrincipal vistaPrincipal = new VistaPrincipal();
            VistaPrincipalController controladorVistaPrincipal = new VistaPrincipalController(vistaPrincipal);
            listaActas.setVisible(false);
        }
    }

    private void llenarTabla() throws SQLException {
        DefaultTableModel modelo = (DefaultTableModel) listaActas.actasJTable.getModel();
        modelo.setRowCount(0);

        List<ActaDeConstatacion> actas = actaJpaController.findAll();
        System.out.println("Total de actas encontradas: " + actas.size());

        // Formateadores de fecha y hora
        // yyyy-MM-dd para Fecha Labrado
        DateTimeFormatter fechaF = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Lo cambié a dd/MM/yyyy para que sea más legible

        // HH:mm:ss para Hora Labrado
        DateTimeFormatter horaF = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Formato para Vencimiento (ej: 01:59:17 Sat Nov 15...) - Asumimos LocalDateTime
        DateTimeFormatter vencimientoF = DateTimeFormatter.ofPattern("HH:mm:ss EEE MMM dd");

        for (ActaDeConstatacion acta : actas) {

            Object[] fila = new Object[]{
                // El ID del acta se omite si no está visible en la imagen,
                // pero lo dejamos aquí ya que estaba en tu código:
                // acta.getIdActa(), 

                // Fecha labrado (Aplicar formato de fecha)
                acta.getFechaDeLabrado() != null
                ? acta.getFechaDeLabrado()// APLICO EL FORMATO
                : "",
                // Hora labrado (Aplicar formato de hora)
                acta.getHoraDeLabrado() != null
                ? acta.getHoraDeLabrado().format(horaF)
                : "",
                // Vencimiento (Aplicar formato combinado)
                acta.getFechaVtoPagoVolun() != null
                ? acta.getFechaVtoPagoVolun() // APLICO EL FORMATO COMBINADO
                : "",
                acta.getLugarDeConstatacion(),
                // Vehículo
                acta.getVehiculo() != null ? acta.getVehiculo().getDominio() : "",
                // Organización
                acta.getOrganizacionEstatal() != null
                ? acta.getOrganizacionEstatal().getNombreOrganizacion()
                : "",
                // Licencia
                acta.getLicencia() != null
                ? acta.getLicencia().getNumeroLicencia()
                : "",
                // Autoridad
                acta.getAutoridadDeConstatacion() != null
                ? acta.getAutoridadDeConstatacion().getNombre()
                : "",
            // Estado del acta
            acta.getEstadoDelActa() != null
                    ? acta.getEstadoDelActa().getDescripcionEstadoActa()
                    : "",
                // Ruta
                acta.getRuta() != null ? acta.getRuta().getNombreRuta() : ""
        };

        modelo.addRow(fila);
    }
}

}
