package com.mycompany.IntegradorMVC.controlador.jpa;

import com.mycompany.IntegradorMVC.modelo.OrganizacionEstatal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;


public class OrganizacionEstatalJpaController extends AbstractJpaController<OrganizacionEstatal> {

    public OrganizacionEstatalJpaController() {
        super(OrganizacionEstatal.class);
    }

    @Override
    public List<OrganizacionEstatal> findAll() {
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaQuery<OrganizacionEstatal> cq = em.getCriteriaBuilder().createQuery(entityClass);
            cq.select(cq.from(entityClass));
            return em.createQuery(cq).getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OrganizacionEstatal> findByNombre(String nombre) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<OrganizacionEstatal> query = em.createQuery(
                    "SELECT o FROM OrganizacionEstatal o WHERE o.nombreOrganizacion LIKE :nombre",
                    OrganizacionEstatal.class
            );
            query.setParameter("nombre", "%" + nombre + "%");

            return query.getResultList();

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}