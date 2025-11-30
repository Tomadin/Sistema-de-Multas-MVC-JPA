/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.IntegradorMVC.controlador.jpa;

import com.mycompany.IntegradorMVC.controlador.jpa.exceptions.NonexistentEntityException;
import com.mycompany.IntegradorMVC.modelo.TipoDeInfraccion;
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
public class TipoDeInfraccionJpaController extends AbstractJpaController<TipoDeInfraccion> {

    public TipoDeInfraccionJpaController() {
        super(TipoDeInfraccion.class);
    }

    /**
     * Busca tipos de infracción por gravedad
     */
    public List<TipoDeInfraccion> findByGravedad(String tipoGravedad) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<TipoDeInfraccion> query = em.createQuery(
                "SELECT t FROM TipoDeInfraccion t WHERE t.tipoGravedad = :gravedad",
                TipoDeInfraccion.class
            );
            query.setParameter("gravedad", tipoGravedad);
            
            return query.getResultList();
            
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Busca tipos de infracción por descripción
     */
    public List<TipoDeInfraccion> findByDescripcion(String descripcion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<TipoDeInfraccion> query = em.createQuery(
                "SELECT t FROM TipoDeInfraccion t WHERE t.descripcionInfraccion LIKE :descripcion",
                TipoDeInfraccion.class
            );
            query.setParameter("descripcion", "%" + descripcion + "%");
            
            return query.getResultList();
            
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Obtiene todos los tipos de gravedad únicos
     */
    public List<String> findAllGravedades() {
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<String> query = em.createQuery(
                "SELECT DISTINCT t.tipoGravedad FROM TipoDeInfraccion t ORDER BY t.tipoGravedad",
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
