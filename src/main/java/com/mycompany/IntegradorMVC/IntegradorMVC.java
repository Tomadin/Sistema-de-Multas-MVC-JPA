package com.mycompany.IntegradorMVC;

import com.mycompany.IntegradorMVC.controlador.vistas.VistaPrincipalController;
import com.mycompany.IntegradorMVC.vista.VistaPrincipal;
import java.sql.SQLException;


public class IntegradorMVC {

    public static void main(String[] args) throws SQLException {
        VistaPrincipal vistaPrincipal = new VistaPrincipal();
        VistaPrincipalController controladorVistaPrincipal = new VistaPrincipalController(vistaPrincipal);

    }
}