package com.mycompany.IntegradorMVC.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "actas")
public class ActaDeConstatacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idActa;
    @OneToMany
    @JoinColumn(name = "acta_id")
    private ArrayList<Infraccion> infracciones = new ArrayList<>();
    private Date fechaDeLabrado; //era LocalDate
    private Date fechaVtoPagoVolun;
    private LocalDateTime horaDeLabrado;
    private String lugarDeConstatacion;
    private String observaciones;
    @ManyToOne
    @JoinColumn(name = "organizacion_id")
    private OrganizacionEstatal organizacionEstatal;
    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;
    @ManyToOne
    @JoinColumn(name = "estado_id")
    private EstadoDelActa estadoDelActa;
    @ManyToOne
    @JoinColumn(name = "autoridad_id")
    private AutoridadDeConstatacion autoridadDeConstatacion;
    @ManyToOne
    @JoinColumn(name = "licencia_id")
    private Licencia licencia;
    @ManyToOne
    @JoinColumn(name = "ruta_id")
    private Ruta ruta;

    public ActaDeConstatacion() {
    }

    public ActaDeConstatacion(Date fechaDeLabrado, Date fechaVtoPagoVolun, LocalDateTime horaDeLabrado, String lugarDeConstatacion, String observaciones, OrganizacionEstatal organizacionEstatal, Vehiculo vehiculo, EstadoDelActa estadoDelActa, AutoridadDeConstatacion autoridadDeConstatacion, Licencia licencia, Ruta ruta) {
        this.fechaDeLabrado = fechaDeLabrado;
        this.fechaVtoPagoVolun = fechaVtoPagoVolun;
        this.horaDeLabrado = horaDeLabrado;
        this.lugarDeConstatacion = lugarDeConstatacion;
        this.observaciones = observaciones;
        this.organizacionEstatal = organizacionEstatal;
        this.vehiculo = vehiculo;
        this.estadoDelActa = estadoDelActa;
        this.autoridadDeConstatacion = autoridadDeConstatacion;
        this.licencia = licencia;
        this.ruta = ruta;
    }

    public ArrayList<Infraccion> getInfracciones() {
        return infracciones;
    }

    public void setInfracciones(ArrayList<Infraccion> infracciones) {
        this.infracciones = infracciones;
    }

    public void addInfraccion(Infraccion infraccion) {
        this.infracciones.add(infraccion);
    }

    public Date getFechaDeLabrado() {
        return fechaDeLabrado;
    }

    public void setFechaDeLabrado(Date fechaDeLabrado) {
        this.fechaDeLabrado = fechaDeLabrado;
    }

    public Date getFechaVtoPagoVolun() {
        return fechaVtoPagoVolun;
    }

    public void setFechaVtoPagoVolun(Date fechaVtoPagoVolun) {
        this.fechaVtoPagoVolun = fechaVtoPagoVolun;
    }

    public LocalDateTime getHoraDeLabrado() {
        return horaDeLabrado;
    }

    public void setHoraDeLabrado(LocalDateTime horaDeLabrado) {
        this.horaDeLabrado = horaDeLabrado;
    }

    public String getLugarDeConstatacion() {
        return lugarDeConstatacion;
    }

    public void setLugarDeConstatacion(String lugarDeConstatacion) {
        this.lugarDeConstatacion = lugarDeConstatacion;
    }

    public int getIdActa() {
        return idActa;
    }

    public void setIdActa(int idActa) {
        this.idActa = idActa;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public OrganizacionEstatal getOrganizacionEstatal() {
        return organizacionEstatal;
    }

    public void setOrganizacionEstatal(OrganizacionEstatal organizacionEstatal) {
        this.organizacionEstatal = organizacionEstatal;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public EstadoDelActa getEstadoDelActa() {
        return estadoDelActa;
    }

    public void setEstadoDelActa(EstadoDelActa estadoDelActa) {
        this.estadoDelActa = estadoDelActa;
    }

    public AutoridadDeConstatacion getAutoridadDeConstatacion() {
        return autoridadDeConstatacion;
    }

    public void setAutoridadDeConstatacion(AutoridadDeConstatacion autoridadDeConstatacion) {
        this.autoridadDeConstatacion = autoridadDeConstatacion;
    }

    public Licencia getLicencia() {
        return licencia;
    }

    public void setLicencia(Licencia licencia) {
        this.licencia = licencia;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    @Override
    public String toString() {
        return "ActaDeConstatacion{" + "infracciones=" + infracciones + ", fechaDeLabrado=" + fechaDeLabrado + ", fechaVtoPagoVolun=" + fechaVtoPagoVolun + ", horaDeLabrado=" + horaDeLabrado + ", lugarDeConstatacion=" + lugarDeConstatacion + ", idActa=" + idActa + ", observaciones=" + observaciones + ", organizacionEstatal=" + organizacionEstatal + ", vehiculo=" + vehiculo + ", estadoDelActa=" + estadoDelActa + ", autoridadDeConstatacion=" + autoridadDeConstatacion + ", licencia=" + licencia + ", ruta=" + ruta + '}';
    }

}
