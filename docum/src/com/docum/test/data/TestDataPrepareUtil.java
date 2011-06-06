package com.docum.test.data;

import java.util.ArrayList;
import java.util.List;

import com.docum.domain.po.IdentifiedEntity;

public class TestDataPrepareUtil {

	public static <T extends IdentifiedEntity, S> List<T> prepareDictionary(
			TestDataPersister persister, S[] names,	TestDataEntityConstructor<T, S> constructor) {
		List<T> result = new ArrayList<T>();
		for(S name : names) {
			result.add(constructor.construct(name));
		}
		persister.persist(result);
		return result;
	}

}
