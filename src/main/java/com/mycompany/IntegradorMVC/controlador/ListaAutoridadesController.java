package com.mycompany.IntegradorMVC.controlador;

import com.mycompany.IntegradorMVC.DAO.AutoridadDAO;
import com.mycompany.IntegradorMVC.modelo.AutoridadDeConstatacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mycompany.IntegradorMVC.vista.ListaAutoridades;

import com.mycompany.IntegradorMVC.vista.VistaPrincipal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ListaAutoridadesController implements ActionListener {

    private ListaAutoridades listaAutoridades;
    private final AutoridadDAO autoridadDAO;

    public ListaAutoridadesController(ListaAutoridades listaAutoridades) {
        this.autoridadDAO = new AutoridadDAO();
        this.listaAutoridades = listaAutoridades;
        this.listaAutoridades.setVisible(true);
        listaAutoridades.backBtn.addActionListener(this);
        cargarTabla();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o.equals(listaAutoridades.backBtn)) {
            VistaPrincipal vistaPrincipal = new VistaPrincipal();
            VistaPrincipalController controladorVistaPrincipal = new VistaPrincipalController(vistaPrincipal);
            listaAutoridades.setVisible(false);

        }
    }

    private void cargarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) listaAutoridades.listaAutoridades.getModel();
        modelo.setRowCount(0); // Limpia la tabla

        List<AutoridadDeConstatacion> autoridades = autoridadDAO.buscarAutoridades();

        for (AutoridadDeConstatacion a : autoridades) {

            Object[] fila = new Object[]{
                a.getNombre(),
                a.getApellido(),
                a.getDni(),
                a.getGenero(),
                a.getIdPlaca(),
                a.getIdLegajo()
            };

            modelo.addRow(fila);
        }

    }

}
