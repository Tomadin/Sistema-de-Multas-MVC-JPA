
package com.mycompany.IntegradorMVC.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="modelos")
public class Modelo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String modeloAuto;

    public Modelo() {
    }

    public Modelo(String modeloAuto) {
        this.modeloAuto = modeloAuto;
    }

    public int getId() {
        return id;
    }
    
    public String getModeloAuto() {
        return modeloAuto;
    }

    public void setModeloAuto(String modeloAuto) {
        this.modeloAuto = modeloAuto;
    }
    
    
}
