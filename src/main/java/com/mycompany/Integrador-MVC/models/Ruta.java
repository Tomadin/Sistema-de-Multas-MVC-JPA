
package com.mycompany.parcialpoo16_10_2025tomadin.models;


public class Ruta {
    private String nombreRuta;
    private String kmRuta;
    private TipoRuta tipoRuta;

    public Ruta(String nombreRuta, String kmRuta, TipoRuta tipoRuta) {
        this.nombreRuta = nombreRuta;
        this.kmRuta = kmRuta;
        this.tipoRuta = tipoRuta;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public String getKmRuta() {
        return kmRuta;
    }

    public void setKmRuta(String kmRuta) {
        this.kmRuta = kmRuta;
    }

    public TipoRuta getTipoRuta() {
        return tipoRuta;
    }

    public void setTipoRuta(TipoRuta tipoRuta) {
        this.tipoRuta = tipoRuta;
    }

    @Override
    public String toString() {
        return "Ruta{" + "nombreRuta=" + nombreRuta + ", kmRuta=" + kmRuta + ", tipoRuta=" + tipoRuta + '}';
    }

    public boolean esRutaInternacional(){
        return false;
    }
    public boolean esRutaNacional(){
        return true;
    }
    public boolean esRutaProvincial(){
        return false;
    }
    
}
