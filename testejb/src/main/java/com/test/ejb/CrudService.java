package com.test.ejb;

import java.util.List;
import java.util.Map;

public interface CrudService {
	<T> T create(T t);
	<T> T find(Object id, Class<T> type);
	<T> T update(T t);
	void delete(Object t);
	<T> List<T> findByNamedQuery(String queryName, Class c);
	List findByNamedQuery(String queryName, int resultLimit);
	List findByNamedQuery(String queryName, Map<String,Object> parameters);
	List findByNamedQuery(String queryName, Map<String,Object> parameters, int resultLimit);

}
