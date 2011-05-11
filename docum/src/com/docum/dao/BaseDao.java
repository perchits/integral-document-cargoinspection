package com.docum.dao;

import java.io.Serializable;

import com.docum.persistence.IdentifiedEntity;

public interface BaseDao extends Serializable {
	
	public <T extends IdentifiedEntity> Long saveObject(T object);
	
	public <T extends IdentifiedEntity> void deleteObject(T object);

}
