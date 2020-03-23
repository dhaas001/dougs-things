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
	public <T> T create(T t) {
		em.persist(t);
		em.flush();
		em.refresh(t);
		return t;
	}

	@Override
	public <T> T find(Object id, Class<T> type) {
		return (T) em.find(type, id);
	}

	@Override
	public <T> T update(T t) {
		return (T)em.merge(t);
	}

	@Override
	public void delete(Object t) {
		Object ref = em.getReference(t.getClass(), t);
		em.remove(ref);

	}

	@Override
	public <T> List<T> findByNamedQuery(String queryName, Class c) {
		return (List<T>) em.createNamedQuery(queryName, c).getResultList();
	}

	@Override
	public List findByNamedQuery(String queryName, int resultLimit) {
		return em.createNamedQuery(queryName).setMaxResults(resultLimit).getResultList();
	}

	@Override
	public List findByNamedQuery(String queryName, Map<String, Object> parameters) {
		return findByNamedQuery(queryName, parameters,0);
	}

	@Override
	public List findByNamedQuery(String queryName, Map<String, Object> parameters, int resultLimit) {
		Set<Entry<String, Object>> rawParams = parameters.entrySet();
		Query query = em.createNamedQuery(queryName);
		if (resultLimit > 0){
			query.setMaxResults(resultLimit);
		}
		for (Entry<String,Object> entry:rawParams){
			query.setParameter(entry.getKey(), entry.getValue());
			
		}
		return query.getResultList();
		
	}

}
