package com.mycompany.IntegradorMVC.controlador.jpa;



import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

public abstract class AbstractJpaController<T> {

    private static EntityManagerFactory emf = null;
    final Class<T> entityClass;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("pruebaJPAPU");
            //logger.log(Level.INFO, "EntityManagerFactory inicializado correctamente");
        } catch (Exception ex) {
            //logger.log(Level.SEVERE, "Error inicializando EntityManagerFactory", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public AbstractJpaController(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    /**
     * Crea una nueva entidad
     */
    public void create(T entity) throws Exception {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            testConnection();

            System.out.println(entity.toString() + " CLase AbstractJPAController");
            em = getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.merge(entity);
            //em.persist(entity);
            em.flush();
            System.out.println("Log: pasó em.persist(entity)");
            tx.commit();

        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error creando entidad", ex);
            ex.printStackTrace();

            throw new Exception("Error al crear: " + ex.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Actualiza una entidad existente
     */
    public void edit(T entity) throws Exception {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            em.merge(entity);

            tx.commit();

        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error actualizando entidad", ex);
            throw new Exception("Error al actualizar: " + ex.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Elimina una entidad por su ID
     */
    public void destroy(Object id) throws Exception {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
            }

            tx.commit();

        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error eliminando entidad", ex);
            throw new Exception("Error al eliminar: " + ex.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Busca una entidad por su ID
     */
    public T find(Object id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            return em.find(entityClass, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Obtiene todas las entidades
     *
     * @return
     */
    public List<T> findAll() {
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(entityClass);
            cq.select(cq.from(entityClass));
            return em.createQuery(cq).getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Obtiene todas las entidades con paginación
     */
    public List<T> findAll(int maxResults, int firstResult) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(entityClass);
            cq.select(cq.from(entityClass));
            TypedQuery<T> q = em.createQuery(cq);
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Cuenta el total de entidades
     */
    public int count() {
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
            cq.select(em.getCriteriaBuilder().count(cq.from(entityClass)));
            return em.createQuery(cq).getSingleResult().intValue();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static void testConnection() {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();

            // Ejecuta una query simple
            em.createNativeQuery("SELECT 1").getSingleResult();

            em.getTransaction().commit();
            System.out.println("✅ Conexión exitosa a la BD");
        } catch (Exception e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
