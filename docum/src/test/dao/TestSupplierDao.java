package test.dao;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;

import test.TestUtil;

import com.docum.persistence.common.Supplier;
import com.docum.service.SupplierService;

public class TestSupplierDao extends TestCase {

	public void testSaveSupplier() {
		ApplicationContext appContext = TestUtil.getSpringApplicationContext();
		assertNotNull(appContext);
		SupplierService supplierService = (SupplierService) appContext.getBean("supplierService");
		assertNotNull(supplierService);
		
		Supplier supplier = new Supplier(TestUtil.getRandomString(10));
		assertNotNull(supplierService.saveSupplier(supplier));
		supplierService.deleteSupplier(supplier);
	}
}
