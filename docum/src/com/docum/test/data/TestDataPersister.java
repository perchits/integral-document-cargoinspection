package com.docum.test.data;

import java.util.Collection;

import com.docum.domain.po.IdentifiedEntity;

public interface TestDataPersister {
	public <T extends IdentifiedEntity> void persist(T entity);
	public <T extends IdentifiedEntity> void persist(Collection<T> entity);
}
