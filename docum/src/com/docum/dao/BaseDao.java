package com.docum.dao;

import java.io.Serializable;
import java.util.List;

import com.docum.persistence.IdentifiedEntity;

public interface BaseDao extends Serializable {
	
	public <T extends IdentifiedEntity> T saveObject(T object);
	
	public <T extends IdentifiedEntity> void deleteObject(T object);
	
	public <T extends IdentifiedEntity> List<T> getAll(Class<T> clazz);

}
