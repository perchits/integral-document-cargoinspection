package com.docum.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.docum.dao.BaseDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/docum-context.xml")
@Transactional
public class TestSupplierDao {
	@Autowired
	BaseDao baseDao;
	
	@Test
	public void testSaveSupplier() {
	/*	Supplier supplier = new Supplier(TestUtil.getRandomString(10));
		assertNotNull(supplierService.saveSupplier(supplier));
		supplierService.deleteSupplier(supplier);*/
	}
}
