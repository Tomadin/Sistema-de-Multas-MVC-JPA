
package com.mycompany.parcialpoo16_10_2025tomadin.models;


public class Vehiculo {
    private String color;
    private String dominio;
    private int anioPatentamiento;
    private Marca marca;

    public Vehiculo(String color, String dominio, int anioPatentamiento, Marca marca) {
        this.color = color;
        this.dominio = dominio;
        this.anioPatentamiento = anioPatentamiento;
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public int getAnioPatentamiento() {
        return anioPatentamiento;
    }

    public void setAnioPatentamiento(int anioPatentamiento) {
        this.anioPatentamiento = anioPatentamiento;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return "Vehiculo{" + "color=" + color + ", dominio=" + dominio + ", anioPatentamiento=" + anioPatentamiento + ", marca=" + marca + '}';
    }
    
    
}
