package br.com.ufscar.dao;

import java.io.Serializable;
import java.util.*;
import java.util.Map.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;

import br.com.ufscar.entity.Item;
 
public class GenericDAO implements Serializable {
 
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit");
    protected EntityManager em;
 
    protected void beginTransaction() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }
 
    private void commit() {
        em.getTransaction().commit();
    }
 
    @SuppressWarnings("unused")
	private void rollback() {
        em.getTransaction().rollback();
    }
 
    protected void closeTransaction() {
        em.close();
    }
 
    private void commitAndCloseTransaction() {
        commit();
        closeTransaction();
    }
 
    @SuppressWarnings("unused")
	private void flush() {
        em.flush();
    }
 
    @SuppressWarnings("unused")
	private void joinTransaction() {
        em = emf.createEntityManager();
        em.joinTransaction();
    }
 
    public <T> void save(T entity) {
    	this.beginTransaction();
        em.persist(entity);
        this.commitAndCloseTransaction();
    }
 
    public <T> T update(T entity) {
    	this.beginTransaction();
    	T result = em.merge(entity);
    	this.commitAndCloseTransaction();
    	return result;
    }
    
//    TODO: criar classe BaseEntity com id para extender o T
//    public <T> T persist(T entity) {
//    	if (entity.getId() == null) {
//    		save(entity);
//    	} else {
//    		update(entity);
//    	}
//    }
    
    public <T> void delete(Object id, Class<T> classe) {
    	this.beginTransaction();
    	T entityToBeRemoved = em.getReference(classe, id);
    	em.remove(entityToBeRemoved);
        this.commitAndCloseTransaction();
    }
 
 
    public <T> T find(Class<T> clazz, Object entityID) {
    	this.beginTransaction();
    	T result = em.find(clazz, entityID);
    	this.closeTransaction();
        return result;
    }
 
    public <T> T findReferenceOnly(Class<T> clazz, int entityID) {
    	this.beginTransaction();
    	T result = em.getReference(clazz, entityID);
    	this.closeTransaction();
        return result;
    }
 
    // Using the unchecked because JPA does not have a
    // em.getCriteriaBuilder().createQuery()<T> method
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> List<T> findAll(Class<T> clazz) {
    	this.beginTransaction();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(clazz));
        List<T> result = em.createQuery(cq).getResultList();
        this.closeTransaction();
        return result;
    }
 
    // Using the unchecked because JPA does not have a
    // query.getSingleResult()<T> method
    @SuppressWarnings("unchecked")
    public <T> T findOneResult(String namedQuery, Map<String, Object> parameters) {
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
    
    @SuppressWarnings("unchecked")
	public <T> T findOneResultByQuery(String namedQuery, Map<String, Object> parameters) {
        T result = null;
 
        try {
        	this.beginTransaction();
            Query query = em.createQuery(namedQuery);
 
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
    
    @SuppressWarnings("unchecked")
	public <T> List<T> findResults(String queryStr, Map<String, Object> parameters) {
    	List<T> result = null;
 
        try {
        	this.beginTransaction();
            Query query = em.createQuery(queryStr);
 
            // Method that will populate parameters if they are passed not null and empty
            if (parameters != null && !parameters.isEmpty()) {
                populateQueryParameters(query, parameters);
            }
 
            result = (List<T>)query.getResultList();
 
        } catch (NoResultException e) {
            System.out.println("No result found for named query: " + queryStr);
        } catch (Exception e) {
            System.out.println("Error while running query: " + e.getMessage());
            e.printStackTrace();
        } finally {
        	this.closeTransaction();
        }
 
        return result;
    }
 
    protected void populateQueryParameters(Query query, Map<String, Object> parameters) {
        for (Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }
    
    public <T> List<T> findByCustomField(Class<T> clazz, String paramName, String value) {
    	String query = "SELECT c FROM " + Item.class.getSimpleName() 
    			+ " c WHERE c." + paramName + " = :param";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("param", value);
		return findResults(query, parameters);
    }
    
    public <T> T findOneByCustomField(Class<T> clazz, String paramName, String value){
    	List<T> results = findByCustomField(clazz, paramName, value);
    	return results == null || results.isEmpty() ? null : results.get(0);
    }
    
}