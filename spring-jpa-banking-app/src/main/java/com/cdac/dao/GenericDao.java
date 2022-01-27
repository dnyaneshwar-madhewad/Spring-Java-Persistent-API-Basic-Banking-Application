package com.cdac.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

@Component
public class GenericDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public void save(Object obj) {
		//merge can be used for insert and update both
		entityManager.merge(obj);
	}
	
	public <E> E findByPK(Class<E> clazz, Object pk) {
		return entityManager.find(clazz, pk);
	}
}
