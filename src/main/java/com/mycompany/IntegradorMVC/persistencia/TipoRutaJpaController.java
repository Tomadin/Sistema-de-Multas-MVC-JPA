/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.IntegradorMVC.persistencia;

import com.mycompany.IntegradorMVC.modelo.TipoRuta;
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
public class TipoRutaJpaController implements Serializable {

    public TipoRutaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoRuta tipoRuta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoRuta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoRuta tipoRuta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tipoRuta = em.merge(tipoRuta);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = tipoRuta.getId();
                if (findTipoRuta(id) == null) {
                    throw new NonexistentEntityException("The tipoRuta with id " + id + " no longer exists.");
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
            TipoRuta tipoRuta;
            try {
                tipoRuta = em.getReference(TipoRuta.class, id);
                tipoRuta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoRuta with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoRuta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoRuta> findTipoRutaEntities() {
        return findTipoRutaEntities(true, -1, -1);
    }

    public List<TipoRuta> findTipoRutaEntities(int maxResults, int firstResult) {
        return findTipoRutaEntities(false, maxResults, firstResult);
    }

    private List<TipoRuta> findTipoRutaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoRuta.class));
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

    public TipoRuta findTipoRuta(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoRuta.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoRutaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoRuta> rt = cq.from(TipoRuta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
