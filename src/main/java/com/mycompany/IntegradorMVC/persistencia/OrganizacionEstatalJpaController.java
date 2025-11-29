/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.IntegradorMVC.persistencia;

import com.mycompany.IntegradorMVC.modelo.OrganizacionEstatal;
import com.mycompany.IntegradorMVC.persistencia.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Tomadin
 */
public class OrganizacionEstatalJpaController implements Serializable {

    public OrganizacionEstatalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrganizacionEstatal organizacionEstatal) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(organizacionEstatal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OrganizacionEstatal organizacionEstatal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            organizacionEstatal = em.merge(organizacionEstatal);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = organizacionEstatal.getId();
                if (findOrganizacionEstatal(id) == null) {
                    throw new NonexistentEntityException("The organizacionEstatal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrganizacionEstatal organizacionEstatal;
            try {
                organizacionEstatal = em.getReference(OrganizacionEstatal.class, id);
                organizacionEstatal.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The organizacionEstatal with id " + id + " no longer exists.", enfe);
            }
            em.remove(organizacionEstatal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OrganizacionEstatal> findOrganizacionEstatalEntities() {
        return findOrganizacionEstatalEntities(true, -1, -1);
    }

    public List<OrganizacionEstatal> findOrganizacionEstatalEntities(int maxResults, int firstResult) {
        return findOrganizacionEstatalEntities(false, maxResults, firstResult);
    }

    private List<OrganizacionEstatal> findOrganizacionEstatalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrganizacionEstatal.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public OrganizacionEstatal findOrganizacionEstatal(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrganizacionEstatal.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrganizacionEstatalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrganizacionEstatal> rt = cq.from(OrganizacionEstatal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
