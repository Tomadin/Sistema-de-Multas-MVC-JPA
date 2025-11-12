
package com.mycompany.IntegradorMVC;

import com.mycompany.IntegradorMVC.controlador.VistaPrincipalController;
import com.mycompany.IntegradorMVC.entidades.ActaDeConstatacion;
import com.mycompany.IntegradorMVC.entidades.AutoridadDeConstatacion;
import com.mycompany.IntegradorMVC.entidades.Conductor;
import com.mycompany.IntegradorMVC.entidades.EstadoDelActa;
import com.mycompany.IntegradorMVC.entidades.Infraccion;
import com.mycompany.IntegradorMVC.entidades.Licencia;
import com.mycompany.IntegradorMVC.entidades.Marca;
import com.mycompany.IntegradorMVC.entidades.Modelo;
import com.mycompany.IntegradorMVC.entidades.OrganizacionEstatal;
import com.mycompany.IntegradorMVC.entidades.Ruta;
import com.mycompany.IntegradorMVC.entidades.TipoDeInfraccion;
import com.mycompany.IntegradorMVC.entidades.TipoRuta;
import com.mycompany.IntegradorMVC.entidades.Vehiculo;
import com.mycompany.IntegradorMVC.vista.VistaPrincipal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;


/**
 *
 * Todo lo solicitado a continuación deben realizarlo a través de un SOLO objeto
 * CON una misma instancia de un ACTA DE CONSTATACIÓN (acta1), MOSTRANDO POR
 * PANTALLA LO SIGUIENTE:
 *
 * 	Numero de Acta 
 * 	Fecha del Acta 
 * 	Dominio del vehículo 
 *      marca 
 * 	Id de licencia 
 * 	Nombre y Km de Ruta 
 * 	Órgano estatal que emitió la Acta de Constatación 
 * 	Localidad, provincia y país del órgano estatal 
 * 	Nombre y apellido del conductor 
 * 	Infracciones Cometidas 
 * 	Número de placa y nombre de la autoridad de constatación
 *
 */
public class IntegradorMVC {

    public static void main(String[] args) {
        
        VistaPrincipal vistaPrincipal = new VistaPrincipal();
        VistaPrincipalController controladorVistaPrincipal = new VistaPrincipalController(vistaPrincipal);
        
    }
}
        
//        //Creamos acta
//        ActaDeConstatacion acta = new ActaDeConstatacion(LocalDate.of(2024, Month.OCTOBER, 9), LocalDate.of(2024, Month.NOVEMBER, 15), LocalDateTime.of(2024, Month.OCTOBER, 9, 12, 30), "San Martin 860", 123123, "Se dio vuelta.", new OrganizacionEstatal("Seguridad Vial", "Lujan de Cuyo"), new Vehiculo("Azul", "BB432NS", 2020, new Marca("Renault", new Modelo("Clio 1.6L"))), new EstadoDelActa("Se labró un acta MUY dura", "Multa"), new AutoridadDeConstatacion(78, 6, "Carlos", "Baute", 23456743, "Masculino"), new Licencia(87642, LocalDate.of(2026, Month.SEPTEMBER, 25), 74), new Ruta("Acceso Sur", "650", new TipoRuta("Asfaltada a medias", "Ruta Miseria")));
//        //Asignamos conductor a la licencia de acta
//        acta.getLicencia().setConductor(new Conductor("Terrada 620", "Dario", "Barassi", 32457643, "Masculino"));
//        //Añadimos infracciones
//        acta.getInfracciones().add(new Infraccion("Se dio vuelta", 50));
//        acta.getInfracciones().add(new Infraccion("Exceso de velocidad", 650000));
//        //añadimos el tipo de infracción a cada infracción
//        acta.getInfracciones().get(0).addInfraccionNomenclada(new TipoDeInfraccion(7, "Se dio vuelta", "Leve", 50, 75));
//        acta.getInfracciones().get(1).addInfraccionNomenclada(new TipoDeInfraccion(17, "Exceso de velocidad (45Km/h)", "GRAVISIMO", 6999999, 0));
//        
//        
//        
//        System.out.println("\nConsigna -----------------------------------------------------------");
//        System.out.println("Numero de Acta: "+acta.getIdActa()+", fecha: "+acta.getFechaDeLabrado()+"\nDominio del Vehiculo: "+acta.getVehiculo().getDominio()+", marca: "+acta.getVehiculo().getMarca().getMarcaAuto()+"\nLicencia id: "+acta.getLicencia().getIdLicencia()+"\nRuta: "+acta.getRuta().getNombreRuta()+" KM "+acta.getRuta().getKmRuta());
//        System.out.println("Organización Emisora de la Multa: "+acta.getOrganizacionEstatal().getNombreOrganizacion()+"\nLocalidad: "+acta.getOrganizacionEstatal().getLocalidad()+", Mendoza, Argentina");
//        System.out.println("Conductor: "+acta.getLicencia().getConductor().getNombre()+", "+acta.getLicencia().getConductor().getApellido());
//        System.out.println("Infracciones Cometidas: ");
//        System.out.println("\tClasificación: "+acta.getInfracciones().get(0).getTipoDeInfraccion().get(0).getTipoGravedad()+", monto: $"+acta.getInfracciones().get(0).getImporteInfraccion()+" - TIPO DE INFRACCION: "+acta.getInfracciones().get(0).getTipoDeInfraccion().get(0).getDescripcionInfraccion()+", descuento: "+acta.getInfracciones().get(0).getTipoDeInfraccion().get(0).getPorcentajeDescuento()+"%\n");
//        System.out.println("\tClasificación: "+acta.getInfracciones().get(1).getTipoDeInfraccion().get(0).getTipoGravedad()+", monto: $"+acta.getInfracciones().get(1).getImporteInfraccion()+" - TIPO DE INFRACCION: "+acta.getInfracciones().get(1).getTipoDeInfraccion().get(0).getDescripcionInfraccion()+", descuento: "+acta.getInfracciones().get(1).getTipoDeInfraccion().get(0).getPorcentajeDescuento()+"%\n");
//        System.out.println("Autoridad a cargo: "+acta.getAutoridadDeConstatacion().getNombre()+", "+acta.getAutoridadDeConstatacion().getApellido()+" N° Placa: "+acta.getAutoridadDeConstatacion().getIdPlaca()+"\n");
//    }

