package com.mycompany.IntegradorMVC.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@Table(name = "licencia")
public class Licencia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int idLicencia;
    private int numeroLicencia;
    
    @Column(name = "fecha_vencimiento")
    private Date fechaDeVto;
    @Column(name = "puntos")
    private int puntosInicialesLicencia;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "conductor_dni", referencedColumnName = "dni")
    private Conductor conductor;

    public Licencia() {
    }

    public Licencia(int numeroLicencia, Date fechaDeVto, int puntosInicialesLicencia) {
        this.numeroLicencia = numeroLicencia;
        this.fechaDeVto = fechaDeVto;
        this.puntosInicialesLicencia = puntosInicialesLicencia;
    }

    public int getIdLicencia() {
        return idLicencia;
    }

    public void setIdLicencia(int idLicencia) {
        this.idLicencia = idLicencia;
    }

    public Date getFechaDeVto() {
        return fechaDeVto;
    }

    public void setFechaDeVto(Date fechaDeVto) {
        this.fechaDeVto = fechaDeVto;
    }

    public int getPuntosInicialesLicencia() {
        return puntosInicialesLicencia;
    }

    public int getNumeroLicencia() {
        return numeroLicencia;
    }

    public void setNumeroLicencia(int numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    public void setPuntosInicialesLicencia(int puntosInicialesLicencia) {
        this.puntosInicialesLicencia = puntosInicialesLicencia;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    @Override
    public String toString() {
        return "Licencia{" + "idLicencia=" + idLicencia + ", fechaDeVto=" + fechaDeVto + ", puntosInicialesLicencia=" + puntosInicialesLicencia + ", conductor=" + conductor + '}';
    }

}
