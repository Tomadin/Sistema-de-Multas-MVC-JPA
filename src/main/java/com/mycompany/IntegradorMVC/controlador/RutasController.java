
package com.mycompany.IntegradorMVC.controlador;

import com.mycompany.IntegradorMVC.vista.ListaRutas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RutasController implements ActionListener{
    public ListaRutas listaRutas;

    public RutasController(ListaRutas vista) {
    this.listaRutas = vista;
    
    this.listaRutas.setVisible(true);
    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
