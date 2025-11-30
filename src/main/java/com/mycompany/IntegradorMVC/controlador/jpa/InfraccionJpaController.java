/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.IntegradorMVC.controlador.jpa;

import com.mycompany.IntegradorMVC.modelo.Infraccion;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author tomad
 */
public class InfraccionJpaController extends AbstractJpaController<Infraccion> {

    public InfraccionJpaController() {
        super(Infraccion.class);
    }

    /**
     * Obtiene todas las infracciones con sus tipos cargados (EAGER)
     */
    @Override
    public List<Infraccion> findAll() {
        EntityManager em = null;
        try {
            em = getEntityManager();

            // ⭐ JPQL CORRECTO: usa alias, NO uses SELECT *
            TypedQuery<Infraccion> query = em.createQuery(
                    "SELECT i FROM Infraccion i", // ⭐ Esto es JPQL
                    Infraccion.class
            );

            List<Infraccion> lista = query.getResultList();

            System.out.println("✅ Infracciones encontradas: " + lista.size());
            for (Infraccion inf : lista) {
                System.out.println("  - " + inf);
            }

            return lista;

        } catch (Exception ex) {
            System.err.println("❌ Error en findAll: " + ex.getMessage());
            ex.printStackTrace();
            Logger.getLogger(InfraccionJpaController.class.getName())
                    .log(Level.SEVERE, "Error buscando infracciones", ex);
            return new ArrayList<>();

        } finally {
            // ⭐ IMPORTANTE: Siempre cerrar en finally
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Busca infracciones por descripción
     */
    public List<Infraccion> findByDescripcion(String descripcion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<Infraccion> query = em.createQuery(
                    "SELECT i FROM Infraccion i WHERE i.descripcionInfraccion LIKE :descripcion",
                    Infraccion.class
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
     * Busca infracciones por rango de importe
     */
    public List<Infraccion> findByImporteRange(double min, double max) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<Infraccion> query = em.createQuery(
                    "SELECT i FROM Infraccion i WHERE i.importeInfraccion BETWEEN :min AND :max",
                    Infraccion.class
            );
            query.setParameter("min", min);
            query.setParameter("max", max);

            return query.getResultList();

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Busca una infracción por ID con sus tipos cargados
     */
    @Override
    public Infraccion find(Object id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<Infraccion> query = em.createQuery(
                    "SELECT i FROM Infraccion i LEFT JOIN FETCH i.infraccionNomenclanda WHERE i.id = :id",
                    Infraccion.class
            );
            query.setParameter("id", id);

            List<Infraccion> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
