/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.IntegradorMVC.controlador.jpa;

import com.mycompany.IntegradorMVC.controlador.jpa.exceptions.NonexistentEntityException;
import com.mycompany.IntegradorMVC.modelo.OrganizacionEstatal;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


/**
 *
 * @author tomad
 */
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

    /**
     * Busca organizaciones por nombre
     */
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

    /**
     * Busca organizaciones por localidad
     */
    public List<OrganizacionEstatal> findByLocalidad(String localidad) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<OrganizacionEstatal> query = em.createQuery(
                    "SELECT o FROM OrganizacionEstatal o WHERE o.localidad = :localidad",
                    OrganizacionEstatal.class
            );
            query.setParameter("localidad", localidad);

            return query.getResultList();

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Obtiene todas las localidades únicas
     */
    public List<String> findAllLocalidades() {
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<String> query = em.createQuery(
                    "SELECT DISTINCT o.localidad FROM OrganizacionEstatal o ORDER BY o.localidad",
                    String.class
            );

            return query.getResultList();

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
