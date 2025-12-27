
package com.mycompany.IntegradorMVC.controlador.jpa;

import com.mycompany.IntegradorMVC.controlador.jpa.exceptions.NonexistentEntityException;
import com.mycompany.IntegradorMVC.modelo.EstadoDelActa;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


public class EstadoDelActaJpaController implements Serializable {

    public EstadoDelActaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoDelActa estadoDelActa) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(estadoDelActa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoDelActa estadoDelActa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            estadoDelActa = em.merge(estadoDelActa);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = estadoDelActa.getId();
                if (findEstadoDelActa(id) == null) {
                    throw new NonexistentEntityException("The estadoDelActa with id " + id + " no longer exists.");
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
            EstadoDelActa estadoDelActa;
            try {
                estadoDelActa = em.getReference(EstadoDelActa.class, id);
                estadoDelActa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoDelActa with id " + id + " no longer exists.", enfe);
            }
            em.remove(estadoDelActa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoDelActa> findEstadoDelActaEntities() {
        return findEstadoDelActaEntities(true, -1, -1);
    }

    public List<EstadoDelActa> findEstadoDelActaEntities(int maxResults, int firstResult) {
        return findEstadoDelActaEntities(false, maxResults, firstResult);
    }

    private List<EstadoDelActa> findEstadoDelActaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoDelActa.class));
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

    public EstadoDelActa findEstadoDelActa(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoDelActa.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoDelActaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoDelActa> rt = cq.from(EstadoDelActa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
