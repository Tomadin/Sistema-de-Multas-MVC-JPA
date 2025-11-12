
package com.mycompany.IntegradorMVC.entidades;


public class AutoridadDeConstatacion extends Persona {
    private int idPlaca;
    private int idLegajo;

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
        return "AutoridadDeConstatacion{" + "idPlaca=" + idPlaca + ", idLegajo=" + idLegajo + '}';
    }
    
    
}
