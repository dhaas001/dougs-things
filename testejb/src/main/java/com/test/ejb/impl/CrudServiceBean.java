package com.test.ejb.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.test.ejb.CrudService;

@Stateless
@Local(CrudService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CrudServiceBean implements CrudService {
	
	@PersistenceContext(unitName = "sample")
	EntityManager em;
	
	

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <T> T create(T t) {
		em.persist(t);
		em.flush();
		em.refresh(t);
		return t;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public <T> T createNewTransaction(T t) {
		em.persist(t);
		em.flush();
		em.refresh(t);
		return t;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <T> T find(Object id, Class<T> type) {
		return (T) em.find(type, id);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <T> T update(T t) {
		return (T)em.merge(t);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public <T> T updateNewTransaction(T t) {
		return (T)em.merge(t);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Object t) {
		Object ref = em.getReference(t.getClass(), t);
		em.remove(ref);

	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void deleteNewTransaction(Object t) {
		Object ref = em.getReference(t.getClass(), t);
		em.remove(ref);

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <T> List<T> findByNamedQuery(String queryName, Class c) {
		return (List<T>)em.createNamedQuery(queryName, c).getResultList();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <T> List<T> findByNamedQuery(String queryName, int resultLimit, Class c) {
		return (List<T>)em.createNamedQuery(queryName, c).setMaxResults(resultLimit).getResultList();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <T> List<T> findByNamedQuery(String queryName, Map<String, Object> parameters, Class c) {
		return (List<T>)findByNamedQuery(queryName, parameters,0, c);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <T> List<T> findByNamedQuery(String queryName, Map<String, Object> parameters, int resultLimit, Class c) {
		Set<Entry<String, Object>> rawParams = parameters.entrySet();
		Query query = em.createNamedQuery(queryName, c);
		if (resultLimit > 0){
			query.setMaxResults(resultLimit);
		}
		for (Entry<String,Object> entry:rawParams){
			query.setParameter(entry.getKey(), entry.getValue());
			
		}
		return query.getResultList();
		
	}

}
