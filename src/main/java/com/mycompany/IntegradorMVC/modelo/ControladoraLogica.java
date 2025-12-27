package com.mycompany.IntegradorMVC.modelo;

import com.mycompany.IntegradorMVC.controlador.jpa.ControladoraPersistencia;
import java.util.List;

/**
 * Controladora de Lógica de Negocio
 * Es la capa intermedia entre los Controladores y la Persistencia
 * @author tomad
 */
public class ControladoraLogica {
    
    private final ControladoraPersistencia controlPersis;
    
    public ControladoraLogica() {
        this.controlPersis = new ControladoraPersistencia();
    }
    
    // ==================== ACTAS DE CONSTATACIÓN ====================
    
    public void crearActa(ActaDeConstatacion acta) throws Exception {
        // Validaciones de negocio
        if (acta == null) {
            throw new Exception("El acta no puede ser nula");
        }
        
        if (acta.getVehiculo() == null) {
            throw new Exception("El acta debe estar asociada a un vehículo");
        }
        
        if (acta.getLicencia() == null) {
            throw new Exception("El acta debe tener una licencia asociada");
        }
        
        if (acta.getAutoridadDeConstatacion() == null) {
            throw new Exception("El acta debe tener una autoridad de constatación");
        }
        
        controlPersis.crearActa(acta);
    }
    
    public List<ActaDeConstatacion> obtenerTodasLasActas() {
        return controlPersis.obtenerTodasLasActas();
    }
    
    // ==================== INFRACCIONES ====================
    
    public void crearInfraccion(Infraccion infraccion) throws Exception {
        // Validaciones de negocio
        if (infraccion == null) {
            throw new Exception("La infracción no puede ser nula");
        }
        
        if (infraccion.getImporteInfraccion() <= 0) {
            throw new Exception("El importe debe ser mayor a 0");
        }
        
        if (infraccion.getDescripcionInfraccion() == null || 
            infraccion.getDescripcionInfraccion().trim().isEmpty()) {
            throw new Exception("La descripción es obligatoria");
        }
        
        // Regla de negocio: Infracciones de alto importe requieren autorización
        if (infraccion.getImporteInfraccion() > 50000) {
            System.out.println("⚠️ Infracción de alto importe - Requiere autorización especial");
        }
        
        controlPersis.crearInfraccion(infraccion);
    }
    
    public List<Infraccion> obtenerTodasLasInfracciones() {
        return controlPersis.obtenerTodasLasInfracciones();
    }
    
    // ==================== VEHÍCULOS ====================

    public void crearVehiculo(Vehiculo vehiculo) throws Exception {
        if (vehiculo == null) {
            throw new Exception("El vehículo no puede ser nulo");
        }
        
        controlPersis.crearVehiculo(vehiculo);
    }
    
    // ==================== CONDUCTORES ====================

    public void crearConductor(Conductor conductor) throws Exception {
        if (conductor == null) {
            throw new Exception("El conductor no puede ser nulo");
        }
        
        if (conductor.getDni() <= 0) {
            throw new Exception("DNI inválido");
        }
        
        if (conductor.getNombre() == null || conductor.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre es obligatorio");
        }
        
        if (conductor.getApellido() == null || conductor.getApellido().trim().isEmpty()) {
            throw new Exception("El apellido es obligatorio");
        }
        
        // Verificar que no exista otro conductor con el mismo DNI
        Conductor existente = controlPersis.obtenerConductor(conductor.getDni());
        if (existente != null) {
            throw new Exception("Ya existe un conductor con DNI " + conductor.getDni());
        }
        
        controlPersis.crearConductor(conductor);
    }

    public List<Conductor> obtenerTodosLosConductores() {
        return controlPersis.obtenerTodosLosConductores();
    }
   
    // ==================== LICENCIAS ====================
    
    public void crearLicencia(Licencia licencia) throws Exception {
        if (licencia == null) {
            throw new Exception("La licencia no puede ser nula");
        }
        
        controlPersis.crearLicencia(licencia);
    }
    
    public Licencia obtenerLicencia(int id) {
        return controlPersis.obtenerLicencia(id);
    }
    
    public List<Licencia> obtenerTodasLasLicencias() {
        return controlPersis.obtenerTodasLasLicencias();
    }
    
    // ==================== AUTORIDADES DE CONSTATACIÓN ====================
    
    public void crearAutoridad(AutoridadDeConstatacion autoridad) throws Exception {
        if (autoridad == null) {
            throw new Exception("La autoridad no puede ser nula");
        }
        
        if (autoridad.getDni() <= 0) {
            throw new Exception("DNI inválido");
        }
        
        if (autoridad.getNombre() == null || autoridad.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre es obligatorio");
        }
        
        // Verificar que no exista otra autoridad con el mismo DNI
        if (controlPersis.existeAutoridadConDni(autoridad.getDni())) {
            throw new Exception("Ya existe una autoridad con DNI " + autoridad.getDni());
        }
        
        controlPersis.crearAutoridad(autoridad);
    }
    
    public List<AutoridadDeConstatacion> obtenerTodasLasAutoridades() {
        return controlPersis.obtenerTodasLasAutoridades();
    }

    public List<OrganizacionEstatal> obtenerTodasLasOrganizaciones() {
        return controlPersis.obtenerTodasLasOrganizaciones();
    }

   
    public double calcularTotalInfraccionesActa(ActaDeConstatacion acta) {
        if (acta == null || acta.getInfracciones() == null) {
            return 0.0;
        }
        
        double total = 0.0;
        for (Infraccion inf : acta.getInfracciones()) {
            total += inf.getImporteInfraccion();
        }
        
        return total;
    }
    
}
