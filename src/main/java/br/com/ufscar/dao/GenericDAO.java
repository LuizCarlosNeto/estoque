package br.com.ufscar.dao;

import java.io.Serializable;
import java.util.*;
import java.util.Map.*;
 
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
 
abstract class GenericDAO<T> implements Serializable {
    private static final long serialVersionUID = 1L;
 
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit");
    private EntityManager em;
 
    private Class<T> entityClass;
 
    private void beginTransaction() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }
 
    private void commit() {
        em.getTransaction().commit();
    }
 
    private void rollback() {
        em.getTransaction().rollback();
    }
 
    private void closeTransaction() {
        em.close();
    }
 
    private void commitAndCloseTransaction() {
        commit();
        closeTransaction();
    }
 
    private void flush() {
        em.flush();
    }
 
    private void joinTransaction() {
        em = emf.createEntityManager();
        em.joinTransaction();
    }
 
    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
 
    public void save(T entity) {
    	this.beginTransaction();
        em.persist(entity);
        this.commitAndCloseTransaction();
    }
 
    protected void delete(Object id, Class<T> classe) {
    	this.beginTransaction();
    	T entityToBeRemoved = em.getReference(classe, id);
    	em.remove(entityToBeRemoved);
        this.commitAndCloseTransaction();
    }
 
    public T update(T entity) {
    	this.beginTransaction();
    	T result = em.merge(entity);
        this.commitAndCloseTransaction();
        return result;
    }
 
    public T find(int entityID) {
    	this.beginTransaction();
    	T result = em.find(entityClass, entityID);
    	this.closeTransaction();
        return result;
    }
 
    public T findReferenceOnly(int entityID) {
    	this.beginTransaction();
    	T result = em.getReference(entityClass, entityID);
    	this.closeTransaction();
        return result;
    }
 
    // Using the unchecked because JPA does not have a
    // em.getCriteriaBuilder().createQuery()<T> method
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<T> findAll() {
    	this.beginTransaction();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        List<T> result = em.createQuery(cq).getResultList();
        this.closeTransaction();
        return result;
    }
 
    // Using the unchecked because JPA does not have a
    // query.getSingleResult()<T> method
    @SuppressWarnings("unchecked")
    protected T findOneResult(String namedQuery, Map<String, Object> parameters) {
        T result = null;
 
        try {
        	this.beginTransaction();
            Query query = em.createNamedQuery(namedQuery);
 
            // Method that will populate parameters if they are passed not null and empty
            if (parameters != null && !parameters.isEmpty()) {
                populateQueryParameters(query, parameters);
            }
 
            result = (T) query.getSingleResult();
            this.closeTransaction();
 
        } catch (NoResultException e) {
            System.out.println("No result found for named query: " + namedQuery);
        } catch (Exception e) {
            System.out.println("Error while running query: " + e.getMessage());
            e.printStackTrace();
        }
 
        return result;
    }
 
    private void populateQueryParameters(Query query, Map<String, Object> parameters) {
        for (Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }
}