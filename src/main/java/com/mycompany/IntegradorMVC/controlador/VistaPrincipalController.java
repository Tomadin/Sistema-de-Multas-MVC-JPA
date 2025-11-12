/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.IntegradorMVC.controlador;

import com.mycompany.IntegradorMVC.vista.ListaRutas;
import com.mycompany.IntegradorMVC.vista.VistaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        vista.rutasJMenuItem.addActionListener(this);
        vista.vehiculosJMenuItem.addActionListener(this);
        vista.organizacionesJMenuItem.addActionListener(this);
        vista.tiposInfraccionesJMenuItem.addActionListener(this);
        vista.tiposRutasJMenuItem.addActionListener(this);
        
    }

    @Override
    public void back() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object o = e.getSource();
        System.out.println(o);

        if (o.equals(vista.rutasJMenuItem)) {
            ListaRutas listaRutas = new ListaRutas();
            RutasController controladorRutas = new RutasController(listaRutas);
        }

//        if(o.equals(vista.gestionActaJMenuItem)){
//            new ListaActasController(this);
//            vista.setVisible(false);
//        } else
//        if(o.equals(vista.nuevaActaJMenuItem)){
//            new NuevaActaController(this);
//            vista.setVisible(false);
//        } 
//        //else if(o.equals(vista.verAutoridadesJMenuItem)){
//            new ListaAutoridadesController(this);
//            vista.setVisible(false);
//        } else if(o.equals(vista.rutasJMenuItem)){
//            new ListaRutasController(this);
//            vista.setVisible(false);
//        }
    }

}
