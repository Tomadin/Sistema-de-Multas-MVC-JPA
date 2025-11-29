
package com.mycompany.IntegradorMVC.modelo;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@DiscriminatorValue("AUTORIDAD")
public class AutoridadDeConstatacion extends Persona implements Serializable {
    private int idPlaca;
    private int idLegajo;
    @Id
    private Long id;

    public AutoridadDeConstatacion() {
    }

    
    public AutoridadDeConstatacion(int idPlaca, int idLegajo, String nombre, String apellido, int dni, String genero) {
        super(nombre, apellido, dni, genero);
        this.idPlaca = idPlaca;
        this.idLegajo = idLegajo;
    }

    public int getIdPlaca() {
        return idPlaca;
    }

    public void setIdPlaca(int idPlaca) {
        this.idPlaca = idPlaca;
    }

    public int getIdLegajo() {
        return idLegajo;
    }

    public void setIdLegajo(int idLegajo) {
        this.idLegajo = idLegajo;
    }

    @Override
    public String toString() {
        return nombre + " - " + apellido;
    }



    public void setId(Long id) {
        this.id = id;
    }
    
    
}
