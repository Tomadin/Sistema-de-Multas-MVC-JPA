
package com.mycompany.IntegradorMVC.controlador.vistas;

import com.mycompany.IntegradorMVC.vista.ListaActas;
import com.mycompany.IntegradorMVC.vista.ListaAutoridades;
import com.mycompany.IntegradorMVC.vista.NuevaActa;
import com.mycompany.IntegradorMVC.vista.NuevaAutoridad;
import com.mycompany.IntegradorMVC.vista.VistaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VistaPrincipalController implements ActionListener {

    public VistaPrincipal vista;

    public VistaPrincipalController(VistaPrincipal vistaPrincipal) {
        this.vista = vistaPrincipal;
        System.out.println(vistaPrincipal);
        vista.setVisible(true);
        vista.gestionActaJMenuItem.addActionListener(this);
        vista.nuevaActaJMenuItem.addActionListener(this);
        vista.verAutoridadesJMenuItem.addActionListener(this);
        vista.nuevaAutoridadJMenuItem.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object o = e.getSource();
        System.out.println(o);

        if(o.equals(vista.nuevaActaJMenuItem)){
            NuevaActa nuevaActa = new NuevaActa();
            ActaController controladorActas = new ActaController(nuevaActa);
        }
        
        vista.setVisible(false);

        if(o.equals(vista.gestionActaJMenuItem)){
            ListaActas listaActas = new ListaActas();
            try {
                ListaActasController listaActasController = new ListaActasController(listaActas);
            } catch (SQLException ex) {
                Logger.getLogger(VistaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
            vista.setVisible(false);
        } 
        
        if(o.equals(vista.verAutoridadesJMenuItem)){
            ListaAutoridades lista = new ListaAutoridades();
            ListaAutoridadesController listaController = new ListaAutoridadesController(lista);
        }
        if(o.equals(vista.nuevaAutoridadJMenuItem)){
            NuevaAutoridad nuevaAutoridad = new NuevaAutoridad();
            NuevaAurodidadController nuevaAutoridadCotroller = new NuevaAurodidadController(nuevaAutoridad);
        }
    }
}
