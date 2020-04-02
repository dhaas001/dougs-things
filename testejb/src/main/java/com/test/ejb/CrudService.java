package com.test.ejb;

import java.util.List;
import java.util.Map;

public interface CrudService {
	<T> T create(T t);
	<T> T createNewTransaction(T t);
	<T> T find(Object id, Class<T> type);
	<T> T update(T t);
	<T> T updateNewTransaction(T t);
	void delete(Object t);
	void deleteNewTransaction(Object t);
	<T> List<T> findByNamedQuery(String queryName, Class c);
	<T> List<T> findByNamedQuery(String queryName, int resultLimit, Class c);
	<T> List<T> findByNamedQuery(String queryName, Map<String,Object> parameters, Class c);
	<T> List<T> findByNamedQuery(String queryName, Map<String,Object> parameters, int resultLimit, Class c);

}
