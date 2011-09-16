package com.docum.test.data;

public abstract class AbstractDataPreparator {

	protected TestDataPersister persister = new DefaultTestDataPersister();

	public AbstractDataPreparator() {
		super();
	}

	public AbstractDataPreparator(TestDataPersister persister) {
		this.persister = persister;
	}

}