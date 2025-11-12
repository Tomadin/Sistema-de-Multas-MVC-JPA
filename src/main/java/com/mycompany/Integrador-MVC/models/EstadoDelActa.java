
package com.mycompany.parcialpoo16_10_2025tomadin.models;


public class EstadoDelActa {
    private String descripcionEstadoActa;
    private String nombreEstadoActa;

    public EstadoDelActa(String descripcionEstadoActa, String nombreEstadoActa) {
        this.descripcionEstadoActa = descripcionEstadoActa;
        this.nombreEstadoActa = nombreEstadoActa;
    }

    public String getDescripcionEstadoActa() {
        return descripcionEstadoActa;
    }

    public void setDescripcionEstadoActa(String descripcionEstadoActa) {
        this.descripcionEstadoActa = descripcionEstadoActa;
    }

    public String getNombreEstadoActa() {
        return nombreEstadoActa;
    }

    public void setNombreEstadoActa(String nombreEstadoActa) {
        this.nombreEstadoActa = nombreEstadoActa;
    }

    @Override
    public String toString() {
        return "EstadoDelActa{" + "descripcionEstadoActa=" + descripcionEstadoActa + ", nombreEstadoActa=" + nombreEstadoActa + '}';
    }
 
}
