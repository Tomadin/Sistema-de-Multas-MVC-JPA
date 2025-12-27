package com.mycompany.IntegradorMVC.controlador.jpa;

import com.mycompany.IntegradorMVC.modelo.TipoDeInfraccion;


public class TipoDeInfraccionJpaController extends AbstractJpaController<TipoDeInfraccion> {

    public TipoDeInfraccionJpaController() {
        super(TipoDeInfraccion.class);
    }
}
