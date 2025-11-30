
package com.mycompany.IntegradorMVC.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="estadodelacta")
public class EstadoDelActa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "descripcion")
    private String descripcionEstadoActa;
    @Column(name = "estado")
    private String nombreEstadoActa;

    public EstadoDelActa() {
    }

    
    public EstadoDelActa(String descripcionEstadoActa, String nombreEstadoActa) {
        this.descripcionEstadoActa = descripcionEstadoActa;
        this.nombreEstadoActa = nombreEstadoActa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
