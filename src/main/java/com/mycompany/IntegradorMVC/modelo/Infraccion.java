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
import javax.persistence.Column;

@Entity
@Table(name = "infraccion")
public class Infraccion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") 
    private int id;
    private String descripcion;
    private double importeInfraccion;
    @ManyToMany
    @JoinTable(
        name = "infraccion_tipoinfraccion",   // ← nombre REAL
        joinColumns = @JoinColumn(name = "infraccion_id"), // ← REAL
        inverseJoinColumns = @JoinColumn(name = "tipo_infraccion_id") // ← REAL
    )
    private ArrayList<TipoDeInfraccion> infraccionNomenclanda = new ArrayList<>();

    public Infraccion() {
    }

    public Infraccion(String descripcionInfraccion, double importeInfraccion) {
        this.descripcion = descripcionInfraccion;
        this.importeInfraccion = importeInfraccion;
    }

    public int getId() {
        return id;
    }

    public String getDescripcionInfraccion() {
        return descripcion;
    }

    public void setDescripcionInfraccion(String descripcionInfraccion) {
        this.descripcion = descripcionInfraccion;
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
        return descripcion + " - $" + importeInfraccion;
    }

}
