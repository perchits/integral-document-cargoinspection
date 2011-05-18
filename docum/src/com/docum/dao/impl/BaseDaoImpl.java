package com.docum.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.docum.dao.BaseDao;
import com.docum.persistence.IdentifiedEntity;

@Repository("baseDao")
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
		TypedQuery<T> query;
		if (sortFields == null) {
			query = entityManager.createQuery(String.format("from %1$s", clazz.getName()),clazz);
		} else {
			String queryString;
			int range = sortFields.length - 1;
			StringBuffer orderString = new StringBuffer();
			for(int i = 0; i < range; i++) {
				orderString.append(sortFields[i]).append(", ");
			}
			orderString.append(sortFields[range]);
			queryString = String.format(
				"from %1$s clazz order by %2$s", clazz.getName(), orderString.toString());
			query = entityManager.createQuery(queryString,clazz);
		}
		List<T> result = query.getResultList();
		return result;
	}

	@Override
	public <T extends IdentifiedEntity> void deleteObject(Class<T> clazz, Long objectId) {
		entityManager.remove(entityManager.find(clazz, objectId));
	}
}
