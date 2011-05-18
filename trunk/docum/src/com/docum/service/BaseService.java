package com.docum.service;

import java.util.List;

import com.docum.persistence.IdentifiedEntity;

public interface BaseService {
	public <T extends IdentifiedEntity> T saveObject(T object);
	public <T extends IdentifiedEntity> void deleteObject(T object);
	public <T extends IdentifiedEntity> void deleteObject(Class<T> clazz, Long objectId);
	public <T extends IdentifiedEntity> T getObject(Class<T> clazz, Long id);
	public <T extends IdentifiedEntity> List<T> getAll(Class<T> clazz, String[] sortFields);
}
