
package com.mycompany.IntegradorMVC.controlador;

import com.mycompany.IntegradorMVC.vista.ListaRutas;
import com.mycompany.IntegradorMVC.vista.VistaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RutasController implements ActionListener{
    public ListaRutas listaRutas;

    public RutasController(ListaRutas vista) {
    this.listaRutas = vista;
    this.listaRutas.setVisible(true);
    listaRutas.backBtn.addActionListener(this);
    listaRutas.detalleRutaBtn.addActionListener(this);
    //traer tabla
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        
        if(o.equals(listaRutas.backBtn)){
            VistaPrincipal vistaPrincipal = new VistaPrincipal();
            VistaPrincipalController controladorVistaPrincipal = new VistaPrincipalController(vistaPrincipal);
            listaRutas.setVisible(false);
            
        }
    }
    
    
}
