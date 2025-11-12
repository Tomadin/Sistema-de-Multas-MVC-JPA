
package com.mycompany.parcialpoo16_10_2025tomadin.models;


public class Conductor extends Persona{
    private String domicilio;

    public Conductor() {
    }

    public Conductor(String domicilio, String nombre, String apellido, int dni, String genero) {
        super(nombre, apellido, dni, genero);
        this.domicilio = domicilio;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public String toString() {
        return "Conductor{" + "domicilio=" + domicilio + '}';
    }

    
}
