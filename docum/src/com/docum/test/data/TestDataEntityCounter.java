package com.docum.test.data;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class TestDataEntityCounter<T> {
	private Collection<T> entities;
	private Iterator<T> it;

	public TestDataEntityCounter(Collection<T> entities) {
		this.entities = entities;
		it = this.entities.iterator();
	}

	public TestDataEntityCounter(T[] entities) {
		this.entities = Arrays.asList(entities);
		it = this.entities.iterator();
	}

	public T next() {
		if (entities.isEmpty()) {
			return null;
		}
		if (!it.hasNext()) {
			it = entities.iterator();
		}
		return it.next();
	}
}