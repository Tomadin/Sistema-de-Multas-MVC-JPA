/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.IntegradorMVC.persistencia;

import com.mycompany.IntegradorMVC.modelo.Infraccion;
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
public class InfraccionJpaController implements Serializable {

    public InfraccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Infraccion infraccion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(infraccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Infraccion infraccion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            infraccion = em.merge(infraccion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = infraccion.getId();
                if (findInfraccion(id) == null) {
                    throw new NonexistentEntityException("The infraccion with id " + id + " no longer exists.");
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
            Infraccion infraccion;
            try {
                infraccion = em.getReference(Infraccion.class, id);
                infraccion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The infraccion with id " + id + " no longer exists.", enfe);
            }
            em.remove(infraccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Infraccion> findInfraccionEntities() {
        return findInfraccionEntities(true, -1, -1);
    }

    public List<Infraccion> findInfraccionEntities(int maxResults, int firstResult) {
        return findInfraccionEntities(false, maxResults, firstResult);
    }

    private List<Infraccion> findInfraccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Infraccion.class));
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

    public Infraccion findInfraccion(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Infraccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getInfraccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Infraccion> rt = cq.from(Infraccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
