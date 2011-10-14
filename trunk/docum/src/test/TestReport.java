package test;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.docum.service.BaseService;
import com.docum.service.ReportingService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/docum-context.xml")
@Transactional
public class TestReport extends TestCase{
	@Autowired
	BaseService baseService;
	@Autowired
	ReportingService reportingService;
	
	@Test
	public void testReport() {
		System.out.println(reportingService.getReportsWithinYear(2011));
	}

}
