package com.docum.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.docum.domain.SortOrderEnum;
import com.docum.domain.po.IdentifiedEntity;

public interface BaseDao extends Serializable {
	
	public <T extends IdentifiedEntity> void deleteObject(T object);
	
	public <T extends IdentifiedEntity> void deleteObject(Class<T> clazz, Long objectId);
	
	public <T extends IdentifiedEntity> T getObject(Class<T> clazz, Long id);
	
	public <T extends IdentifiedEntity> List<T> getAll(Class<T> clazz, Map<String, SortOrderEnum>sortFields);	


	public <T extends IdentifiedEntity> T save(T entity);

	public <T extends IdentifiedEntity> List<T> save(List<T> entities);
	
	public <T extends IdentifiedEntity> Set<T> save(Set<T> entities);
}
