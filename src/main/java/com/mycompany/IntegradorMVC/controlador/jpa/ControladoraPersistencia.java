package com.mycompany.IntegradorMVC.controlador.jpa;

import com.mycompany.IntegradorMVC.modelo.ActaDeConstatacion;
import com.mycompany.IntegradorMVC.modelo.AutoridadDeConstatacion;
import com.mycompany.IntegradorMVC.modelo.Conductor;
import com.mycompany.IntegradorMVC.modelo.Infraccion;
import com.mycompany.IntegradorMVC.modelo.Licencia;
import com.mycompany.IntegradorMVC.modelo.Vehiculo;
import com.mycompany.IntegradorMVC.modelo.OrganizacionEstatal;
import java.util.List;

/**
 * Controladora de Persistencia
 * Esta clase centraliza y unifica TODAS las operaciones de persistencia
 * Se comunica con los JpaControllers específicos de cada entidad
 * 
 * @author tomad
 */
public class ControladoraPersistencia {
 // ==================== JPA CONTROLLERS ====================
    private ActaDeConstatacionJpaController actaJpa;
    private AutoridadDeConstatacionJpaController autoridadJpa;
    private ConductorJpaController conductorJpa;
    private InfraccionJpaController infraccionJpa;
    private LicenciaJpaController licenciaJpa;
    private VehiculoJpaController vehiculoJpa;
    private OrganizacionEstatalJpaController organizacionJpa;
    // Agrega los demás controllers según necesites
    
    /**
     * Constructor - Inicializa todos los controladores JPA
     */
    public ControladoraPersistencia() {
        this.actaJpa = new ActaDeConstatacionJpaController();
        this.autoridadJpa = new AutoridadDeConstatacionJpaController();
        this.conductorJpa = new ConductorJpaController();
        this.infraccionJpa = new InfraccionJpaController();
        this.licenciaJpa = new LicenciaJpaController();
        this.vehiculoJpa = new VehiculoJpaController(JPAUtil.getEntityManager().getEntityManagerFactory());
        this.organizacionJpa = new OrganizacionEstatalJpaController();
        
        // Inicializa los demás...
    }
    
    // ==================== ACTAS DE CONSTATACIÓN ====================
    
    public void crearActa(ActaDeConstatacion acta) {
        try {
            actaJpa.create(acta);
        } catch (Exception e) {
            System.err.println("Error al crear acta: " + e.getMessage());
            throw new RuntimeException("Error al crear acta de constatación", e);
        }
    }
    
    public List<ActaDeConstatacion> obtenerTodasLasActas() {
        try {
            return actaJpa.findAll();
        } catch (Exception e) {
            System.err.println("Error al obtener actas: " + e.getMessage());
            return null;
        }
    }
    
    // ==================== INFRACCIONES ====================
    
    public void crearInfraccion(Infraccion infraccion) {
        try {
            infraccionJpa.create(infraccion);
        } catch (Exception e) {
            System.err.println("Error al crear infracción: " + e.getMessage());
            throw new RuntimeException("Error al crear infracción", e);
        }
    }
    
    public Infraccion obtenerInfraccion(int id) {
        try {
            return infraccionJpa.find(id);
        } catch (Exception e) {
            System.err.println("Error al obtener infracción: " + e.getMessage());
            return null;
        }
    }
    
    public List<Infraccion> obtenerTodasLasInfracciones() {
        try {
            return infraccionJpa.findAll();
        } catch (Exception e) {
            System.err.println("Error al obtener infracciones: " + e.getMessage());
            return null;
        }
    }
    
    // ==================== VEHÍCULOS ====================
    
    public void crearVehiculo(Vehiculo vehiculo) {
        try {
            vehiculoJpa.create(vehiculo);
        } catch (Exception e) {
            System.err.println("Error al crear vehículo: " + e.getMessage());
            throw new RuntimeException("Error al crear vehículo", e);
        }
    }
    
    public Vehiculo obtenerVehiculo(int id) {
        try {
            return vehiculoJpa.findVehiculo(id);
        } catch (Exception e) {
            System.err.println("Error al obtener vehículo: " + e.getMessage());
            return null;
        }
    }
    
    public List<Vehiculo> obtenerTodosLosVehiculos() {
        try {
            return vehiculoJpa.findVehiculoEntities();
        } catch (Exception e) {
            System.err.println("Error al obtener vehículos: " + e.getMessage());
            return null;
        }
    }
    
    // ==================== CONDUCTORES ====================
    
    public void crearConductor(Conductor conductor) {
        try {
            conductorJpa.create(conductor);
        } catch (Exception e) {
            System.err.println("Error al crear conductor: " + e.getMessage());
            throw new RuntimeException("Error al crear conductor", e);
        }
    }
    
    public Conductor obtenerConductor(int dni) {
        try {
            return conductorJpa.findConductor(dni);
        } catch (Exception e) {
            System.err.println("Error al obtener conductor: " + e.getMessage());
            return null;
        }
    }
    
    public List<Conductor> obtenerTodosLosConductores() {
        try {
            return conductorJpa.findConductorEntities();
        } catch (Exception e) {
            System.err.println("Error al obtener conductores: " + e.getMessage());
            return null;
        }
    }
  
    // ==================== LICENCIAS ====================
    
    public void crearLicencia(Licencia licencia) {
        try {
            licenciaJpa.create(licencia);
        } catch (Exception e) {
            System.err.println("Error al crear licencia: " + e.getMessage());
            throw new RuntimeException("Error al crear licencia", e);
        }
    }
    
    public Licencia obtenerLicencia(int id) {
        try {
            return licenciaJpa.findLicencia(id);
        } catch (Exception e) {
            System.err.println("Error al obtener licencia: " + e.getMessage());
            return null;
        }
    }
    
    public List<Licencia> obtenerTodasLasLicencias() {
        try {
            return licenciaJpa.findLicenciaEntities();
        } catch (Exception e) {
            System.err.println("Error al obtener licencias: " + e.getMessage());
            return null;
        }
    }
  
    // ==================== AUTORIDADES DE CONSTATACIÓN ====================
    
    public void crearAutoridad(AutoridadDeConstatacion autoridad) {
        try {
            autoridadJpa.create(autoridad);
        } catch (Exception e) {
            System.err.println("Error al crear autoridad: " + e.getMessage());
            throw new RuntimeException("Error al crear autoridad", e);
        }
    }
    
    public AutoridadDeConstatacion obtenerAutoridad(int id) {
        try {
            return autoridadJpa.find(id);
        } catch (Exception e) {
            System.err.println("Error al obtener autoridad: " + e.getMessage());
            return null;
        }
    }
    
    public List<AutoridadDeConstatacion> obtenerTodasLasAutoridades() {
        try {
            return autoridadJpa.findAll();
        } catch (Exception e) {
            System.err.println("Error al obtener autoridades: " + e.getMessage());
            return null;
        }
    }
    
    public boolean existeAutoridadConDni(int dni) {
        try {
            return autoridadJpa.existsByDni(dni);
        } catch (Exception e) {
            System.err.println("Error al verificar DNI de autoridad: " + e.getMessage());
            return false;
        }
    }

    public List<OrganizacionEstatal> obtenerTodasLasOrganizaciones() {
        try {
            return organizacionJpa.findAll();
        } catch (Exception e) {
            System.err.println("Error al obtener organizaciones: " + e.getMessage());
            return null;
        }
    }
  

}