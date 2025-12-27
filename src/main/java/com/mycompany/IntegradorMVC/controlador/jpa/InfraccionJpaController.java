
package com.mycompany.IntegradorMVC.controlador.jpa;

import com.mycompany.IntegradorMVC.modelo.Infraccion;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;


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
            if (em != null) {
                em.close();
            }
        }
    }
}
