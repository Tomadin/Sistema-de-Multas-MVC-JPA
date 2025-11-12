
package com.mycompany.IntegradorMVC.entidades;


public class TipoRuta {
    private String descTipoRuta;
    private String nombreTipoDeRuta;

    public TipoRuta(String descTipoRuta, String nombreTipoDeRuta) {
        this.descTipoRuta = descTipoRuta;
        this.nombreTipoDeRuta = nombreTipoDeRuta;
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
