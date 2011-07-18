package com.docum.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docum.dao.BaseDao;
import com.docum.domain.SortOrderEnum;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.service.BaseService;

@Service("baseService")
@Transactional
public class BaseServiceImpl implements BaseService {
	private static final long serialVersionUID = 233634615322331476L;
	@Autowired
	BaseDao baseDao;

	@Override
	public <T extends IdentifiedEntity> void deleteObject(T object) {
		baseDao.deleteObject(object);
	}

	@Override
	public <T extends IdentifiedEntity> void deleteObject(Class<T> clazz,
			Long objectId) {
		baseDao.deleteObject(clazz, objectId);
	}

	@Override
	public <T extends IdentifiedEntity> T getObject(Class<T> clazz, Long id) {
		return baseDao.getObject(clazz, id);
	}

	@Override
	public <T extends IdentifiedEntity> List<T> getAll(Class<T> clazz) {
		return baseDao.getAll(clazz, null);
	}

	@Override
	public <T extends IdentifiedEntity> List<T> getAll(Class<T> clazz,
			Map<String, SortOrderEnum> sortFields) {
		return baseDao.getAll(clazz, sortFields);
	}

	@Override
	public <T extends IdentifiedEntity> T save(T entity) {
		return baseDao.save(entity);
	}

	public <T extends IdentifiedEntity> List<T> save(List<T> entities) {
		return baseDao.save(entities);
	}
	
	public <T extends IdentifiedEntity> Set<T> save(Set<T> entities) {
		return baseDao.save(entities);
	}
	
}
