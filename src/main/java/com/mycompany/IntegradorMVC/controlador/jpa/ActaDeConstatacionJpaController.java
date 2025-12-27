
package com.mycompany.IntegradorMVC.controlador.jpa;

import com.mycompany.IntegradorMVC.modelo.ActaDeConstatacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author tomad
 */
public class ActaDeConstatacionJpaController extends AbstractJpaController<ActaDeConstatacion> {

    public ActaDeConstatacionJpaController() {
        super(ActaDeConstatacion.class);
    }


    @Override
    public List<ActaDeConstatacion> findAll() {
        EntityManager em = null;
        try {
            em = getEntityManager();

            // ⭐ QUERY SIMPLE PRIMERO - para verificar que funciona
            TypedQuery<ActaDeConstatacion> query = em.createQuery(
                    "SELECT a FROM ActaDeConstatacion a "
                    + "LEFT JOIN FETCH a.vehiculo "
                    + "LEFT JOIN FETCH a.organizacionEstatal "
                    + "LEFT JOIN FETCH a.licencia "
                    + "LEFT JOIN FETCH a.autoridadDeConstatacion "
                    + "LEFT JOIN FETCH a.estadoDelActa "
                    + "LEFT JOIN FETCH a.ruta",
                    ActaDeConstatacion.class
            );

            List<ActaDeConstatacion> actas = query.getResultList();
            System.out.println("✅ Actas encontradas: " + actas.size());

            for (ActaDeConstatacion acta : actas) {
                System.out.println("Nombre Autoridad - 48 JPACONTROLLER: "+acta.getAutoridadDeConstatacion().getNombre());
            }
            
            return actas;

        } catch (Exception ex) {
            System.err.println("❌ Error en findAll: " + ex.getMessage());
            ex.printStackTrace();
            return new ArrayList<>();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Busca un acta por ID con todas sus relaciones cargadas
     */
    @Override
    public ActaDeConstatacion find(Object id) {
        EntityManager em = null;
        try {
            em = getEntityManager();

            TypedQuery<ActaDeConstatacion> query = em.createQuery(
                    "SELECT a FROM ActaDeConstatacion a "
                    + "LEFT JOIN FETCH a.organizacionEstatal "
                    + "LEFT JOIN FETCH a.vehiculo v "
                    + "LEFT JOIN FETCH v.marca m "
                    + "LEFT JOIN FETCH m.modelo "
                    + "LEFT JOIN FETCH a.estadoDelActa "
                    + "LEFT JOIN FETCH a.autoridadDeConstatacion "
                    + "LEFT JOIN FETCH a.licencia l "
                    + "LEFT JOIN FETCH l.conductor "
                    + "LEFT JOIN FETCH a.ruta r "
                    + "LEFT JOIN FETCH r.tipoRuta "
                    + "LEFT JOIN FETCH a.infracciones i "
                    + "LEFT JOIN FETCH i.infraccionNomenclanda "
                    + "WHERE a.idActa = :id",
                    ActaDeConstatacion.class
            );
            query.setParameter("id", id);

            List<ActaDeConstatacion> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
