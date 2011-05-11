package test.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import test.TestUtil;

import com.docum.persistence.common.Supplier;
import com.docum.service.SupplierService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/docum-context.xml")
@TransactionConfiguration(transactionManager="documTxManager")
@Transactional
public class TestSupplierDao {
	@Autowired
	SupplierService supplierService;
	
	@Test
	public void testSaveSupplier() {
		Supplier supplier = new Supplier(TestUtil.getRandomString(10));
		assertNotNull(supplierService.saveSupplier(supplier));
		supplierService.deleteSupplier(supplier);
		supplierService.getAllSuppliers();
	}
}
