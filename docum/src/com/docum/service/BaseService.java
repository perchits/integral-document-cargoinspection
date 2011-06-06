package com.docum.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.docum.domain.SortOrderEnum;
import com.docum.domain.po.IdentifiedEntity;

public interface BaseService extends Serializable {
	public <T extends IdentifiedEntity> T saveObject(T object);
	public <T extends IdentifiedEntity> T updateObject(T object);
	public <T extends IdentifiedEntity> void deleteObject(T object);
	public <T extends IdentifiedEntity> void deleteObject(Class<T> clazz, Long objectId);
	public <T extends IdentifiedEntity> T getObject(Class<T> clazz, Long id);
	public <T extends IdentifiedEntity> List<T> getAll(Class<T> clazz, Map<String, SortOrderEnum> sortFields);
}
