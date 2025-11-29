/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.IntegradorMVC.persistencia;

import com.mycompany.IntegradorMVC.modelo.ActaDeConstatacion;
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
public class ActaDeConstatacionJpaController implements Serializable {

    public ActaDeConstatacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ActaDeConstatacion actaDeConstatacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(actaDeConstatacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ActaDeConstatacion actaDeConstatacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            actaDeConstatacion = em.merge(actaDeConstatacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = actaDeConstatacion.getIdActa();
                if (findActaDeConstatacion(id) == null) {
                    throw new NonexistentEntityException("The actaDeConstatacion with id " + id + " no longer exists.");
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
            ActaDeConstatacion actaDeConstatacion;
            try {
                actaDeConstatacion = em.getReference(ActaDeConstatacion.class, id);
                actaDeConstatacion.getIdActa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actaDeConstatacion with id " + id + " no longer exists.", enfe);
            }
            em.remove(actaDeConstatacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ActaDeConstatacion> findActaDeConstatacionEntities() {
        return findActaDeConstatacionEntities(true, -1, -1);
    }

    public List<ActaDeConstatacion> findActaDeConstatacionEntities(int maxResults, int firstResult) {
        return findActaDeConstatacionEntities(false, maxResults, firstResult);
    }

    private List<ActaDeConstatacion> findActaDeConstatacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ActaDeConstatacion.class));
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

    public ActaDeConstatacion findActaDeConstatacion(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ActaDeConstatacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getActaDeConstatacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ActaDeConstatacion> rt = cq.from(ActaDeConstatacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
