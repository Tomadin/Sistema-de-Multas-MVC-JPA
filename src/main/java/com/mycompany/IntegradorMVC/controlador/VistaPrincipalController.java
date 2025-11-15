/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.IntegradorMVC.controlador;

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

/**
 *
 * @author tomad
 */
public class VistaPrincipalController extends AbstractController implements ActionListener {

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
    public void back() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

//        //else if(o.equals(vista.verAutoridadesJMenuItem)){
//            new ListaAutoridadesController(this);
//            vista.setVisible(false);
//        } else if(o.equals(vista.rutasJMenuItem)){
//            new ListaRutasController(this);
//            vista.setVisible(false);
//        }
    }

}
