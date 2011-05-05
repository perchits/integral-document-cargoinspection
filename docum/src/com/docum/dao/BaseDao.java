package com.docum.dao;

import com.docum.persistence.DocumEntity;

public interface BaseDao {
	
	public <T extends DocumEntity> Long saveObject(T object);
	
	public <T extends DocumEntity> void deleteObject(T object);

}
