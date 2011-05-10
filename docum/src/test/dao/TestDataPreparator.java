package test.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.docum.domain.ContainerStateEnum;
import com.docum.persistence.common.BillOfLading;
import com.docum.persistence.common.Container;
import com.docum.persistence.common.Invoice;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/docum-context.xml")
@TransactionConfiguration(transactionManager="documTxManager", defaultRollback=false)
@Transactional
public class TestDataPreparator {

	@PersistenceContext(name="docum")
	private EntityManager entityManager;

	@Test
//	@Rollback(false)
	public void prepareData(){
		List<Container> containers = prepareContainers();
		List<BillOfLading> bills = prepareBillOfLadings(containers);
		List<Invoice> invoices = prepareInvoices(containers);
	
	}
	
	private List<Container> prepareContainers() {
		List<Container> result = new ArrayList<Container>();
		result.add(new Container("ZCSU5836992", ContainerStateEnum.BEFORE_CUSTOMS));
		result.add(new Container("ZCSU5132123", ContainerStateEnum.AFTER_CUSTOMS));
		result.add(new Container("ZCSU5853644", ContainerStateEnum.CHECKED));
		result.add(new Container("ZCSU5879274", ContainerStateEnum.FINISHED));
		result.add(new Container("ZCSU5836626", ContainerStateEnum.BEFORE_CUSTOMS));
		result.add(new Container("ZCSU5846814", ContainerStateEnum.AFTER_CUSTOMS));
		result.add(new Container("ZCSU5836992", ContainerStateEnum.CHECKED));
		result.add(new Container("ZCSU5879398", ContainerStateEnum.FINISHED));
		
		for(Container container : result){
			entityManager.persist(container);
		}

		return result;
	}
	
	private List<BillOfLading> prepareBillOfLadings(List<Container> containers){
		List<BillOfLading> result = new ArrayList<BillOfLading>();

		BillOfLading bill0 = new BillOfLading("ZIMUASH032310");
		BillOfLading bill1 = new BillOfLading("ZIMUHFA00322361");
		
		bill0.getContainers().add(containers.get(0));
		containers.get(0).getBillOfLadings().add(bill0);
		bill0.getContainers().add(containers.get(1));
		containers.get(1).getBillOfLadings().add(bill0);
		bill0.getContainers().add(containers.get(2));
		containers.get(2).getBillOfLadings().add(bill0);
		bill0.getContainers().add(containers.get(3));
		containers.get(3).getBillOfLadings().add(bill0);

		bill1.getContainers().add(containers.get(2));
		containers.get(2).getBillOfLadings().add(bill1);
		bill1.getContainers().add(containers.get(4));
		containers.get(4).getBillOfLadings().add(bill1);
		bill1.getContainers().add(containers.get(5));
		containers.get(5).getBillOfLadings().add(bill1);
		bill1.getContainers().add(containers.get(6));
		containers.get(6).getBillOfLadings().add(bill1);
		bill1.getContainers().add(containers.get(7));
		containers.get(7).getBillOfLadings().add(bill1);

		result.add(bill0);
		result.add(bill1);
		for(BillOfLading bill : result){
			entityManager.persist(bill);
		}

		return result;
	}
	
	private List<Invoice> prepareInvoices(List<Container> containers){
		List<Invoice> result = new ArrayList<Invoice>();

		Invoice invoice0 = new Invoice("PP11-652");
		invoice0.getContainers().add(containers.get(0));
		containers.get(0).getInvoices().add(invoice0);
		invoice0.getContainers().add(containers.get(1));
		containers.get(1).getInvoices().add(invoice0);

		Invoice invoice1 = new Invoice("PP11-678");
		invoice1.getContainers().add(containers.get(2));
		containers.get(2).getInvoices().add(invoice1);
		invoice1.getContainers().add(containers.get(3));
		containers.get(3).getInvoices().add(invoice1);

		Invoice invoice2 = new Invoice("TM11-674");
		invoice2.getContainers().add(containers.get(4));
		containers.get(4).getInvoices().add(invoice2);
		invoice2.getContainers().add(containers.get(5));
		containers.get(5).getInvoices().add(invoice2);

		Invoice invoice3 = new Invoice("CL11-675");
		invoice3.getContainers().add(containers.get(6));
		containers.get(6).getInvoices().add(invoice3);
		invoice3.getContainers().add(containers.get(7));
		containers.get(7).getInvoices().add(invoice3);

		result.add(invoice0);
		result.add(invoice1);
		result.add(invoice2);
		result.add(invoice3);
		
		for(Invoice invoice : result){
			entityManager.persist(invoice);
		}

		return result;
	}
}
