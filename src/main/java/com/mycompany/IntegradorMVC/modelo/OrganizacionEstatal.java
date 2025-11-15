
package com.mycompany.IntegradorMVC.modelo;


public class OrganizacionEstatal {
    private int id;
    private String nombreOrganizacion;
    private String localidad;

    public OrganizacionEstatal() {
    }

    
    public OrganizacionEstatal(String nombreOrganizacion, String localidad) {
        this.nombreOrganizacion = nombreOrganizacion;
        this.localidad = localidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public String getNombreOrganizacion() {
        return nombreOrganizacion;
    }

    public void setNombreOrganizacion(String nombreOrganizacion) {
        this.nombreOrganizacion = nombreOrganizacion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    @Override
    public String toString() {
        return id+" - "+nombreOrganizacion +" - "+localidad;
    }
    
    
}
