package br.com.ufscar.dao.common;

import java.util.Map;

public interface GenericQuery<T> {

	boolean hasParameters();

	Map<String, Object> getParameters();

	void setParameter(String name, Object value);

	void setParameters(Map<String, Object> parameters);

	void setPage(Integer page);

	void setPageCount(Integer pageCount);

	void setMaxResults(Integer maxResults);

	void setFirstResult(Integer firstResult);

	Integer getMaxResults();

	Integer getFirstResult();

	Integer getPage();

	Integer getPageCount();

}
