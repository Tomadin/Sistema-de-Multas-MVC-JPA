
package com.mycompany.parcialpoo16_10_2025tomadin.models;

import java.util.ArrayList;


public class Infraccion {
    private String descripcionInfraccion;
    private double importeInfraccion;
    private ArrayList<TipoDeInfraccion> infraccionNomenclanda = new ArrayList<>();

    public Infraccion(String descripcionInfraccion, double importeInfraccion) {
        this.descripcionInfraccion = descripcionInfraccion;
        this.importeInfraccion = importeInfraccion;
    }

    public String getDescripcionInfraccion() {
        return descripcionInfraccion;
    }

    public void setDescripcionInfraccion(String descripcionInfraccion) {
        this.descripcionInfraccion = descripcionInfraccion;
    }

    public double getImporteInfraccion() {
        return importeInfraccion;
    }

    public void setImporteInfraccion(double importeInfraccion) {
        this.importeInfraccion = importeInfraccion;
    }

    public ArrayList<TipoDeInfraccion> getInfraccionNomenclanda() {
        return infraccionNomenclanda;
    }

    public void setInfraccionNomenclanda(ArrayList<TipoDeInfraccion> infraccionNomenclanda) {
        this.infraccionNomenclanda = infraccionNomenclanda;
    }
    
    public void addInfraccionNomenclada(TipoDeInfraccion tipoDeInfraccion){
        this.infraccionNomenclanda.add(tipoDeInfraccion);
    }

    public ArrayList<TipoDeInfraccion> getTipoDeInfraccion(){
        return infraccionNomenclanda;
    }
    
    @Override
    public String toString() {
        return "Infraccion{" + "descripcionInfraccion=" + descripcionInfraccion + ", importeInfraccion=" + importeInfraccion + ", infraccionNomenclanda=" + infraccionNomenclanda + '}';
    }
    
    
}
