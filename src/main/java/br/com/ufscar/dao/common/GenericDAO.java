package br.com.ufscar.dao.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GenericDAO extends AutoCloseable {

	Map<String, ?> EMPTY_PARAMETERS = new HashMap<>();

	@Override
	void close();

	<T> GenericQuery<T> createNamedQuery(String name, Class<T> clazz);

	<T> GenericQuery<T> createNativeQueryByName(String queryName, Class<T> clazz);

	<T> GenericQuery<T> createNativeQuery(String query, Class<T> clazz);

	<T> GenericQuery<T> createQuery(String query, Class<T> clazz);

	Long execute(GenericQuery<Void> query);

	Long count(Class<?> clazz);

	void delete(Object entity);

	void deleteById(Class<?> clazz, Object id);

	void insert(Object entity);

	<T> T refresh(T entity);

	<T> T update(T entity);

	<T> T findById(Class<T> clazz, Object id);

	<T> T findByQuery(GenericQuery<T> query);

	<T> List<T> list(Class<T> clazz);

	<T> List<T> listByQuery(GenericQuery<T> query);

	<T> T unwrap(Class<T> clazz);

}
