package com.docum.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.docum.dao.BaseDao;
import com.docum.domain.SortOrderEnum;
import com.docum.domain.po.IdentifiedEntity;

@Repository("baseDao")
public class BaseDaoImpl implements BaseDao {
	private static final long serialVersionUID = -3085216261035616856L;

	@PersistenceContext(name = "docum")
	protected EntityManager entityManager;

	protected BaseDaoImpl() {
	}

	public <T extends IdentifiedEntity> void deleteObject(T object) {
		entityManager.remove(object);
	}

	public <T extends IdentifiedEntity> T getObject(Class<T> clazz, Long id) {
		return entityManager.find(clazz, id);
	}

	public <T extends IdentifiedEntity> List<T> getAll(Class<T> clazz,
			Map<String, SortOrderEnum> sortFields) {		
		StringBuffer orderBy = new StringBuffer();
		if (sortFields != null) {
			if (orderBy.length() > 0) {
				orderBy.append(", ");
			}
			for (String key : sortFields.keySet()) {
				orderBy.append(String.format(" clazz.%1$s %2$s ", key,
						sortFields.get(key)));
			}
			orderBy.insert(0, " order by ");
		}
		String queryString = String.format("from %1$s clazz %2$s",
				clazz.getName(), orderBy.toString());
		TypedQuery<T> query = entityManager.createQuery(queryString, clazz);
		List<T> result = query.getResultList();
		return result;
	}

	@Override
	public <T extends IdentifiedEntity> void deleteObject(Class<T> clazz,
			Long objectId) {
		entityManager.remove(entityManager.find(clazz, objectId));
	}

	@Override
	public <T extends IdentifiedEntity> T save(T entity) {
		if (entity.getId() == null) {
			entityManager.persist(entity);
		} else {
			if (!entityManager.contains(entity)) {
				entity = entityManager.merge(entity);
			}
		}
		return entity;
	}

	private <T extends IdentifiedEntity, C extends Collection<T>> C save(C result, C entities) {
		for (T entity : entities) {
			result.add(save(entity));
		}			
		return result;
	}
	
	@Override
	public <T extends IdentifiedEntity> List<T> save(List<T> entities) {
		return save(new ArrayList<T>(entities.size()), entities);
	}
	
	@Override
	public <T extends IdentifiedEntity> Set<T> save(Set<T> entities) {
		return save(new HashSet<T>(entities.size()), entities);
	}
}
