package com.mycompany.IntegradorMVC.controlador;

import com.mycompany.IntegradorMVC.DAO.ActaDeConstatacionDAO;
import com.mycompany.IntegradorMVC.modelo.ActaDeConstatacion;
import com.mycompany.IntegradorMVC.vista.ListaActas;
import com.mycompany.IntegradorMVC.vista.VistaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ListaActasController implements ActionListener {

    public ListaActas listaActas;
    public final ActaDeConstatacionDAO actaDAO;

    public ListaActasController(ListaActas vista) {
        this.listaActas = vista;
        this.listaActas.setVisible(true);
        this.actaDAO = new ActaDeConstatacionDAO();
        llenarTabla();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void llenarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) listaActas.actasJTable.getModel();
        modelo.setRowCount(0); // BORRAR FILAS ANTERIORES

        List<ActaDeConstatacion> actas = actaDAO.obtenerActas();

        for (ActaDeConstatacion acta : actas) {

            Object[] fila = new Object[]{
                acta.getIdActa(),
                acta.getFechaDeLabrado(), // java.util.Date
                acta.getLugarDeConstatacion(),
                acta.getOrganizacionEstatal() != null
                ? acta.getOrganizacionEstatal().getNombreOrganizacion()
                : "—",
                
            };

            modelo.addRow(fila);
        }
    }

}
