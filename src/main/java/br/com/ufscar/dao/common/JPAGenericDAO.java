package br.com.ufscar.dao.common;

import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import br.com.ufscar.dao.common.JPAGenericQuery.JPAQueryType;

public class JPAGenericDAO implements GenericDAO {

	private final EntityManager entityManager;
	private final NativeSQL nativeSQL;
	private final GenericTransaction transaction;

	private boolean startedTransaction = false;

	public JPAGenericDAO(EntityManager entityManager, GenericTransaction transaction, NativeSQL nativeSQL) {
		this.entityManager = entityManager;
		this.nativeSQL = nativeSQL;
		this.transaction = transaction;
	}

	@Override
	public void close() throws RuntimeException {
		if (startedTransaction) {
			transaction.close();
		}
	}

	@Override
	public Long count(Class<?> clazz) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

		criteria = criteria.select(builder.count(criteria.from(clazz)));

		return asObject(entityManager.createQuery(criteria));

	}

	@Override
	public <T> GenericQuery<T> createNamedQuery(String name, Class<T> clazz) {
		Query jpaQuery = clazz.isAssignableFrom(Void.class) ?
				entityManager.createNamedQuery(name) :
				entityManager.createNamedQuery(name, clazz);
		return new JPAGenericQuery<T>(jpaQuery, name, JPAQueryType.NAMED);
	}

	@Override
	public <T> GenericQuery<T> createNativeQuery(String query, Class<T> clazz) {
		Query jpaQuery = clazz.isAssignableFrom(Void.class) ?
				entityManager.createNativeQuery(query) :
				entityManager.createNativeQuery(query, clazz);
		return new JPAGenericQuery<T>(jpaQuery, query, JPAQueryType.NATIVE);
	}

	@Override
	public <T> GenericQuery<T> createNativeQueryByName(String queryName, Class<T> clazz) {
		String sql = nativeSQL.byName(queryName);
		return createNativeQuery(sql, clazz);
	}

	@Override
	public <T> GenericQuery<T> createQuery(String query, Class<T> clazz) {
		Query jpaQuery = clazz.isAssignableFrom(Void.class) ?
				entityManager.createQuery(query) :
				entityManager.createQuery(query, clazz);
		return new JPAGenericQuery<T>(jpaQuery, query, JPAQueryType.QUERY);
	}

	@Override
	public void delete(Object entity) {
		joinTransactionIfExists();
		entityManager.remove(entity);
	}

	@Override
	public void deleteById(Class<?> clazz, Object id) {
		joinTransactionIfExists();
		delete(findById(clazz, id));
	}

	@Override
	public Long execute(GenericQuery<Void> query) {
		joinTransactionIfExists();
		return Long.valueOf(configQuery(query).executeUpdate());
	}

	@Override
	public <T> T findById(Class<T> clazz, Object id) {
		return entityManager.find(clazz, id);
	}

	@Override
	public <T> T findByQuery(GenericQuery<T> query) {
		return asObject(configQuery(query));
	}

	@Override
	public void insert(Object entity) {
		joinTransactionIfExists();
		entityManager.persist(entity);
	}

	@Override
	public <T> List<T> list(Class<T> clazz) {

		CriteriaQuery<T> criteria = entityManager.getCriteriaBuilder().createQuery(clazz);

		criteria = criteria.select(criteria.from(clazz));

		return asList(entityManager.createQuery(criteria));

	}

	@Override
	public <T> List<T> listByQuery(GenericQuery<T> query) {
		return asList(configQuery(query));
	}

	@Override
	public <T> T refresh(T entity) {
		entityManager.refresh(entity);
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> clazz) {
		return (T) (clazz == EntityManager.class ? entityManager : entityManager.unwrap(clazz));
	}

	@Override
	public <T> T update(T entity) {
		joinTransactionIfExists();
		return entityManager.merge(entity);
	}

	@SuppressWarnings("unchecked")
	private <T> JPAGenericQuery<T> asJpaGenericQuery(GenericQuery<T> query) {
		return (JPAGenericQuery<T>) (JPAGenericQuery<?>) query;
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> asList(Query query) {
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	private <T> T asObject(Query query) {
		try {
			return (T) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	private void configParameters(Query query, GenericQuery<?> genericQuery) {
		for (Entry<String, Object> entry : genericQuery.getParameters().entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}

	private Query configQuery(GenericQuery<?> genericQuery) {

		Query query = asJpaGenericQuery(genericQuery).getQueryObject();

		configParameters(query, genericQuery);

		return query;

	}

	private void joinTransactionIfExists() {
		if (!transaction.isActive()) {
			transaction.begin();
			startedTransaction = true;
		}
	}

}