
package com.mycompany.IntegradorMVC.controlador.jpa;

import com.mycompany.IntegradorMVC.modelo.AutoridadDeConstatacion;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;



public class AutoridadDeConstatacionJpaController extends AbstractJpaController<AutoridadDeConstatacion> {

    public AutoridadDeConstatacionJpaController() {
        super(AutoridadDeConstatacion.class);
    }

    public boolean existsByDni(int dni) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(a) FROM AutoridadDeConstatacion a WHERE a.dni = :dni",
                    Long.class
            );
            query.setParameter("dni", dni);

            return query.getSingleResult() > 0;

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    
}
