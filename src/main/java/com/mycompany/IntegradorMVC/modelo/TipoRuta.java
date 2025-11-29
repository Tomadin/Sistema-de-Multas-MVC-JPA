
package com.mycompany.IntegradorMVC.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tiposRutas")
public class TipoRuta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String descTipoRuta;
    private String nombreTipoDeRuta;

    public TipoRuta() {
    }

    public TipoRuta(String descTipoRuta, String nombreTipoDeRuta) {
        this.descTipoRuta = descTipoRuta;
        this.nombreTipoDeRuta = nombreTipoDeRuta;
    }

    public int getId() {
        return id;
    }
    public String getDescTipoRuta() {
        return descTipoRuta;
    }

    public void setDescTipoRuta(String descTipoRuta) {
        this.descTipoRuta = descTipoRuta;
    }

    public String getNombreTipoDeRuta() {
        return nombreTipoDeRuta;
    }

    public void setNombreTipoDeRuta(String nombreTipoDeRuta) {
        this.nombreTipoDeRuta = nombreTipoDeRuta;
    }
    
    public boolean esInternacional(){
        return false;
    }
    public boolean esNacional(){
        return true;
    }
    public boolean esProvincial(){
        return false;
    }
}
