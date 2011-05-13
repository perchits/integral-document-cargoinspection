package com.docum.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.docum.dao.BaseDao;
import com.docum.persistence.IdentifiedEntity;

//@Repository("baseDao")
public class BaseDaoImpl implements BaseDao {
	private static final long serialVersionUID = -3085216261035616856L;

	@PersistenceContext(name="docum")
	protected EntityManager entityManager;

	protected BaseDaoImpl() {
	}
	
	public <T extends IdentifiedEntity> T saveObject(T object) {
//		entityManager.getTransaction().begin();
		entityManager.persist(object);
		entityManager.flush();
//		entityManager.getTransaction().commit();
		return object;
	}

	public <T extends IdentifiedEntity> void deleteObject(T object) {
		entityManager.remove(object);
	}
	
	public <T extends IdentifiedEntity> List<T> getAll(Class<T> clazz) {
		List<T> result = null;
		TypedQuery<T> query = entityManager.createQuery(
			String.format("from %1$s", clazz.getName()),clazz);
		result = query.getResultList();
		return result;
	}
}
