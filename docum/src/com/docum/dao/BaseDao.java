package com.docum.dao;

import com.docum.persistence.IdentifiedEntity;

public interface BaseDao {
	
	public <T extends IdentifiedEntity> Long saveObject(T object);
	
	public <T extends IdentifiedEntity> void deleteObject(T object);

}
