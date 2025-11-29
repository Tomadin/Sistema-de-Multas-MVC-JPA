package com.mycompany.IntegradorMVC.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;

@Entity
@Table(name = "infracciones")
public class Infraccion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String descripcionInfraccion;
    private double importeInfraccion;
    @ManyToMany
    @JoinTable(
            name = "infraccion_tipo",
            joinColumns = @JoinColumn(name = "infraccion_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_infraccion_id")
    )
    private ArrayList<TipoDeInfraccion> infraccionNomenclanda = new ArrayList<>();

    public Infraccion() {
    }

    public Infraccion(String descripcionInfraccion, double importeInfraccion) {
        this.descripcionInfraccion = descripcionInfraccion;
        this.importeInfraccion = importeInfraccion;
    }

    public int getId() {
        return id;
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

    public void addInfraccionNomenclada(TipoDeInfraccion tipoDeInfraccion) {
        this.infraccionNomenclanda.add(tipoDeInfraccion);
    }

    public ArrayList<TipoDeInfraccion> getTipoDeInfraccion() {
        return infraccionNomenclanda;
    }

    @Override
    public String toString() {
        return descripcionInfraccion + " - $" + importeInfraccion;
    }

}
