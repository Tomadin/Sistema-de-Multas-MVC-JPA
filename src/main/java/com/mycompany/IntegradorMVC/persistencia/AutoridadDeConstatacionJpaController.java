/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.IntegradorMVC.persistencia;

import com.mycompany.IntegradorMVC.modelo.AutoridadDeConstatacion;
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
public class AutoridadDeConstatacionJpaController implements Serializable {

    public AutoridadDeConstatacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AutoridadDeConstatacion autoridadDeConstatacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(autoridadDeConstatacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AutoridadDeConstatacion autoridadDeConstatacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            autoridadDeConstatacion = em.merge(autoridadDeConstatacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = autoridadDeConstatacion.getId();
                if (findAutoridadDeConstatacion(id) == null) {
                    throw new NonexistentEntityException("The autoridadDeConstatacion with id " + id + " no longer exists.");
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
            AutoridadDeConstatacion autoridadDeConstatacion;
            try {
                autoridadDeConstatacion = em.getReference(AutoridadDeConstatacion.class, id);
                autoridadDeConstatacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The autoridadDeConstatacion with id " + id + " no longer exists.", enfe);
            }
            em.remove(autoridadDeConstatacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AutoridadDeConstatacion> findAutoridadDeConstatacionEntities() {
        return findAutoridadDeConstatacionEntities(true, -1, -1);
    }

    public List<AutoridadDeConstatacion> findAutoridadDeConstatacionEntities(int maxResults, int firstResult) {
        return findAutoridadDeConstatacionEntities(false, maxResults, firstResult);
    }

    private List<AutoridadDeConstatacion> findAutoridadDeConstatacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AutoridadDeConstatacion.class));
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

    public AutoridadDeConstatacion findAutoridadDeConstatacion(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AutoridadDeConstatacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getAutoridadDeConstatacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AutoridadDeConstatacion> rt = cq.from(AutoridadDeConstatacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
