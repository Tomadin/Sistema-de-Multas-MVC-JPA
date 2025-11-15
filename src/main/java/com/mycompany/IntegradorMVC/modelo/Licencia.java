
package com.mycompany.IntegradorMVC.modelo;

import java.time.LocalDate;
import java.util.Date;


public class Licencia {
    private int idLicencia;
    private Date fechaDeVto;
    private int puntosInicialesLicencia;
    private Conductor conductor = new Conductor();

    public Licencia() {
    }

    
    public Licencia(int idLicencia, Date fechaDeVto, int puntosInicialesLicencia) {
        this.idLicencia = idLicencia;
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
