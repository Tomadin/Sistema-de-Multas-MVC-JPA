/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.IntegradorMVC.persistencia;

import com.mycompany.IntegradorMVC.modelo.TipoDeInfraccion;
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
public class TipoDeInfraccionJpaController implements Serializable {

    public TipoDeInfraccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoDeInfraccion tipoDeInfraccion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoDeInfraccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoDeInfraccion tipoDeInfraccion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tipoDeInfraccion = em.merge(tipoDeInfraccion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = tipoDeInfraccion.getId_infrac();
                if (findTipoDeInfraccion(id) == null) {
                    throw new NonexistentEntityException("The tipoDeInfraccion with id " + id + " no longer exists.");
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
            TipoDeInfraccion tipoDeInfraccion;
            try {
                tipoDeInfraccion = em.getReference(TipoDeInfraccion.class, id);
                tipoDeInfraccion.getId_infrac();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoDeInfraccion with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoDeInfraccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoDeInfraccion> findTipoDeInfraccionEntities() {
        return findTipoDeInfraccionEntities(true, -1, -1);
    }

    public List<TipoDeInfraccion> findTipoDeInfraccionEntities(int maxResults, int firstResult) {
        return findTipoDeInfraccionEntities(false, maxResults, firstResult);
    }

    private List<TipoDeInfraccion> findTipoDeInfraccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoDeInfraccion.class));
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

    public TipoDeInfraccion findTipoDeInfraccion(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoDeInfraccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoDeInfraccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoDeInfraccion> rt = cq.from(TipoDeInfraccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
