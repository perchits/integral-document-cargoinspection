package com.docum.test.data;

import java.util.Collection;

import com.docum.domain.po.IdentifiedEntity;

public class DefaultTestDataPersister implements TestDataPersister {
	private static long id = 0;
	
	@Override
	public <T extends IdentifiedEntity> void persist(T entity) {
		if(entity != null) {
			entity.setId(++id);
		}
	}

	@Override
	public <T extends IdentifiedEntity> void persist(Collection<T> entities) {
		for(T entity : entities) {
			persist(entity);
		}
	}
}
