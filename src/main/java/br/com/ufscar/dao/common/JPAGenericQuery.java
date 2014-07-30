package br.com.ufscar.dao.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Query;

class JPAGenericQuery<T> implements GenericQuery<T> {

	public static enum JPAQueryType {
		QUERY, NAMED, NATIVE, CRITERIA
	}

	private final Map<String, Object> parameters = new HashMap<>();

	private final Object querySource;
	private final Query queryObject;
	private final JPAQueryType type;

	private Integer page;
	private Integer pageCount;

	public JPAGenericQuery(Query queryObject, Object querySource, JPAQueryType type) {
		this.queryObject = queryObject;
		this.querySource = querySource;
		this.type = type;
	}

	@Override
	public Integer getFirstResult() {
		return queryObject.getFirstResult();
	}

	@Override
	public Integer getMaxResults() {
		return queryObject.getMaxResults();
	}

	@Override
	public Integer getPage() {
		return page;
	}

	@Override
	public Integer getPageCount() {
		return pageCount;
	}

	@Override
	public Map<String, Object> getParameters() {
		return Collections.unmodifiableMap(parameters);
	}

	public Query getQueryObject() {
		return queryObject;
	}

	public Object getQuerySource() {
		return querySource;
	}

	public JPAQueryType getType() {
		return type;
	}

	@Override
	public boolean hasParameters() {
		return !parameters.isEmpty();
	}

	@Override
	public void setFirstResult(Integer firstResult) {
		queryObject.setFirstResult(firstResult);
	}

	@Override
	public void setMaxResults(Integer maxResults) {
		queryObject.setMaxResults(maxResults);
	}

	@Override
	public void setPage(Integer page) {
		this.page = page;
	}

	@Override
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	@Override
	public void setParameter(String name, Object value) {
		this.parameters.put(name, value);
	}

	@Override
	public void setParameters(Map<String, Object> parameters) {
		for (Entry<String, Object> entry : parameters.entrySet()) {
			this.setParameter(entry.getKey(), entry.getValue());
		}
	}

}
