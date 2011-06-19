package com.docum.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
	public <T extends IdentifiedEntity> T saveObject(T object) {
		return baseDao.saveObject(object);
	}

	@Override
	public <T extends IdentifiedEntity> Collection<T> mergeObjects(Collection<T> objects) {
		return baseDao.mergeObjects(objects);
	}
	
	@Override
	public <T extends IdentifiedEntity> T updateObject(T object) {
		return baseDao.updateObject(object);
	}

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
	public <T extends IdentifiedEntity> List<T> getAll(Class<T> clazz,
			Map<String, SortOrderEnum> sortFields) {
		return baseDao.getAll(clazz, sortFields);
	}

}
