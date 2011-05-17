package test.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import com.docum.persistence.common.Article;
import com.docum.persistence.common.BillOfLading;
import com.docum.persistence.common.Cargo;
import com.docum.persistence.common.City;
import com.docum.persistence.common.Container;
import com.docum.persistence.common.Invoice;
import com.docum.persistence.common.Supplier;
import com.docum.persistence.common.Vessel;
import com.docum.persistence.common.Voyage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/docum-context.xml")
@TransactionConfiguration(defaultRollback=false)
@Transactional
public class TestDataPreparator {

	@PersistenceContext(name="docum")
	private EntityManager entityManager;
	
	Calendar cal = new GregorianCalendar(
			Calendar.getInstance().get(Calendar.YEAR),
			Calendar.getInstance().get(Calendar.MONTH),
			Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

	@Test
//	@Rollback(false)
	public void prepareData(){
		List<Supplier> suppliers = prepareSuppliers();
		List<Article> articles = prepareArticles();
		List<Vessel> vessels = prepareVessels();
		List<Voyage> voyages = prepareVoyages(vessels);
		List<Container> containers = prepareContainers(voyages);
		List<Cargo> cargoes = prepareCargoes(articles, suppliers, containers);
		List<BillOfLading> bills = prepareBillOfLadings(containers);
		List<Invoice> invoices = prepareInvoices(containers);
		List<City> cites = prepareCities();
	
	}
	
	private List<Voyage> prepareVoyages(List<Vessel> vessels) {
		Calendar pastCal = (Calendar) cal.clone();
		pastCal.add(Calendar.MONTH, -1);
		Calendar futureCal = (Calendar) cal.clone();
		futureCal.add(Calendar.MONTH, 1);
		List<Voyage> result = new ArrayList<Voyage>();
		result.add(new Voyage(vessels.get(0), "13/W", pastCal.getTime(), true));
		result.add(new Voyage(vessels.get(1), "51/E", cal.getTime(), true));
		result.add(new Voyage(vessels.get(2), "40/E", futureCal.getTime(), false));
		for(Voyage voyage : result){
			entityManager.persist(voyage);
		}
		return result;
	}

	private List<Vessel> prepareVessels() {
		List<Vessel> result = new ArrayList<Vessel>();
		result.add(new Vessel("Zim Pacific"));
		result.add(new Vessel("Zim India"));
		result.add(new Vessel("Michigan Trader"));
		for(Vessel vessel : result){
			entityManager.persist(vessel);
		}
		return result;
	}

	private List<Article> prepareArticles() {
		List<Article> result = new ArrayList<Article>();
		result.add(new Article("Помело свежий", "Помело", "Fresh Pomelo"));
		result.add(new Article("Чеснок свежий", "Чеснок", "Fresh Garlic"));
		result.add(new Article("Яблоки свежие", "Яблоки", "Fresh Apples"));
		result.add(new Article("Грейпфрут свежий", "Грейпфрут", "Fresh Grapefruit"));
		result.add(new Article("Картофель свежий", "Картофель", "Fresh Potatoes"));
		for(Article article : result){
			entityManager.persist(article);
		}
		return result;
	}

	private List<Supplier> prepareSuppliers() {
		List<Supplier> result = new ArrayList<Supplier>();
		result.add(new Supplier("Qingdao Yunlong Import And Export Co. Ltd."));
		result.add(new Supplier("U.M.S. Industry And Food Ltd."));
		result.add(new Supplier("Jining Sanglong Economic & Trade Co. Ltd."));
		for(Supplier supplier : result){
			entityManager.persist(supplier);
		}
		return result;
	}

	private List<Container> prepareContainers(List<Voyage> voyages) {
		List<Container> result = new ArrayList<Container>();
		result.add(new Container("ZCSU5836992", ContainerStateEnum.BEFORE_CUSTOMS, voyages.get(0)));
		result.add(new Container("ZCSU5132123", ContainerStateEnum.AFTER_CUSTOMS, voyages.get(1)));
		result.add(new Container("ZCSU5853644", ContainerStateEnum.CHECKED, voyages.get(2)));
		result.add(new Container("ZCSU5879274", ContainerStateEnum.FINISHED, voyages.get(0)));
		result.add(new Container("ZCSU5836626", ContainerStateEnum.BEFORE_CUSTOMS, voyages.get(1)));
		result.add(new Container("ZCSU5846814", ContainerStateEnum.AFTER_CUSTOMS, voyages.get(2)));
		result.add(new Container("ZCSU5836992", ContainerStateEnum.CHECKED, voyages.get(0)));
		result.add(new Container("ZCSU5879398", ContainerStateEnum.FINISHED, voyages.get(1)));
		
		for(Container container : result){
			entityManager.persist(container);
		}

		return result;
	}

	private List<Cargo> prepareCargoes(List<Article> articles,
			List<Supplier> suppliers, List<Container> containers) {
		List<Cargo> result = new ArrayList<Cargo>();

		Cargo cargo0 = new Cargo(articles.get(0), 12300.0, suppliers.get(0), containers.get(0));
		Cargo cargo1 = new Cargo(articles.get(1), 22300.0, suppliers.get(1), containers.get(1));
		Cargo cargo2 = new Cargo(articles.get(2), 24200.0, suppliers.get(2), containers.get(2));
		Cargo cargo3 = new Cargo(articles.get(3), 20520.0, suppliers.get(0), containers.get(3));
		Cargo cargo4 = new Cargo(articles.get(4), 19800.0, suppliers.get(1), containers.get(4));
		Cargo cargo5 = new Cargo(articles.get(0), 25000.0, suppliers.get(2), containers.get(5));
		Cargo cargo6 = new Cargo(articles.get(1), 23300.0, suppliers.get(0), containers.get(6));
		Cargo cargo7 = new Cargo(articles.get(2), 21630.0, suppliers.get(1), containers.get(7));
		Cargo cargo8 = new Cargo(articles.get(3), 26300.0, suppliers.get(2), containers.get(0));
		Cargo cargo9 = new Cargo(articles.get(4), 8700.0, suppliers.get(0), containers.get(1));
		
		result.add(cargo0);
		result.add(cargo1);
		result.add(cargo2);
		result.add(cargo3);
		result.add(cargo4);
		result.add(cargo5);
		result.add(cargo6);
		result.add(cargo7);
		result.add(cargo8);
		result.add(cargo9);

		for(Cargo cargo : result){
			entityManager.persist(cargo);
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
		
		bill1.getContainers().add(containers.get(4));
		containers.get(4).getBillOfLadings().add(bill1);
		bill1.getContainers().add(containers.get(5));
		containers.get(5).getBillOfLadings().add(bill1);
		bill1.getContainers().add(containers.get(6));
		containers.get(6).getBillOfLadings().add(bill1);
		bill1.getContainers().add(containers.get(7));
		containers.get(7).getBillOfLadings().add(bill1);
		bill1.getContainers().add(containers.get(0));
		containers.get(0).getBillOfLadings().add(bill1);

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
	
	private List<City> prepareCities() {
		List<City> result = new ArrayList<City>();
		result.add(new City("Новороссийск",true));
		result.add(new City("Анапа",true));
		result.add(new City("Темрюк",false));
		for(City city : result){
			entityManager.persist(city);
		}
		return result;
	}
}
