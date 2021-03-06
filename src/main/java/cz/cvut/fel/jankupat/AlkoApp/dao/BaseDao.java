package cz.cvut.fel.jankupat.AlkoApp.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * The type Base dao.
 *
 * @param <T> the type parameter
 */
public abstract class BaseDao<T> implements GenericDao<T> {

    /**
     * The Em.
     */
    @PersistenceContext
    protected EntityManager em;

    /**
     * The Type.
     */
    protected final Class<T> type;

    /**
     * Instantiates a new Base dao.
     *
     * @param type the type
     */
    protected BaseDao(Class<T> type) {
        this.type = type;
    }

    @Override
    public T find(Integer id) {
        Objects.requireNonNull(id);
        return em.find(type, id);
    }

    @Override
    public List<T> findAll() {
        try {
            return em.createQuery("SELECT e FROM " + type.getSimpleName() + " e", type).getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Find all by ads list.
     *
     * @param ids the ids
     * @return the list
     */
    public List<T> findAllByAds(Set<Integer> ids) {
        try {
            return em.createQuery("SELECT e FROM " + type.getSimpleName() + " e WHERE id IN ?1", type).setParameter(1, ids).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void persist(T entity) {
        Objects.requireNonNull(entity);
        try {
            em.persist(entity);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void persist(Collection<T> entities) {
        Objects.requireNonNull(entities);
        if (entities.isEmpty()) {
            return;
        }
        try {
            entities.forEach(this::persist);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public T update(T entity) {
        Objects.requireNonNull(entity);
        try {
            return em.merge(entity);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void remove(T entity) {
        Objects.requireNonNull(entity);
        try {
            final T toRemove = em.merge(entity);
            if (toRemove != null) {
                em.remove(toRemove);
            }
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean exists(Integer id) {
        return id != null && em.find(type, id) != null;
    }
}
