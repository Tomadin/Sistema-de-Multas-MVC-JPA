
package com.mycompany.IntegradorMVC.entidades;


public class OrganizacionEstatal {
    private String nombreOrganizacion;
    private String localidad;

    public OrganizacionEstatal(String nombreOrganizacion, String localidad) {
        this.nombreOrganizacion = nombreOrganizacion;
        this.localidad = localidad;
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
        return "OrganizacionEstatal{" + "nombreOrganizacion=" + nombreOrganizacion + ", localidad=" + localidad + '}';
    }
    
    
}
