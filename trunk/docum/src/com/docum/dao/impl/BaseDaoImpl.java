package com.docum.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.docum.dao.BaseDao;
import com.docum.persistence.IdentifiedEntity;

public class BaseDaoImpl implements BaseDao {
	private static final long serialVersionUID = -3085216261035616856L;

	@PersistenceContext(name="docum")
	protected EntityManager entityManager;

	protected BaseDaoImpl() {
	}
	
	public <T extends IdentifiedEntity> T saveObject(T object) {
		entityManager.persist(object);
		entityManager.flush();
		return object;
	}

	public <T extends IdentifiedEntity> void deleteObject(T object) {
		entityManager.remove(object);
	}
	
	public <T extends IdentifiedEntity> T getObject(Class<T> clazz, Long id) {
		return entityManager.find(clazz, id);
	}
	
	public <T extends IdentifiedEntity> List<T> getAll(Class<T> clazz, String[] sortFields) {
		String queryString;
		TypedQuery<T> query;
		if (sortFields == null) {
			queryString = "from %1$s";
		} else {
			queryString = "from %1$s clazz order by name";
		}
		query = entityManager.createQuery(String.format(queryString, clazz.getName()),clazz);
		List<T> result = query.getResultList();
		return result;
	}
}
