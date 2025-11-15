
package com.mycompany.IntegradorMVC.modelo;


public class Marca {
    private String marcaAuto;
    private Modelo modelo;

    public Marca(String marcaAuto, Modelo modelo) {
        this.marcaAuto = marcaAuto;
        this.modelo = modelo;
    }

    public String getMarcaAuto() {
        return marcaAuto;
    }

    public void setMarcaAuto(String marcaAuto) {
        this.marcaAuto = marcaAuto;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }
    
    private String queModelo(){
        return modelo.getModeloAuto();
    }
}
