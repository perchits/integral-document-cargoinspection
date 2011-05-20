package test.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
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
import com.docum.persistence.IdentifiedEntity;
import com.docum.persistence.common.Article;
import com.docum.persistence.common.BillOfLading;
import com.docum.persistence.common.Cargo;
import com.docum.persistence.common.City;
import com.docum.persistence.common.Container;
import com.docum.persistence.common.Invoice;
import com.docum.persistence.common.Measure;
import com.docum.persistence.common.PurchaseOrder;
import com.docum.persistence.common.Supplier;
import com.docum.persistence.common.Tare;
import com.docum.persistence.common.Vessel;
import com.docum.persistence.common.Voyage;
import com.docum.util.AlgoUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/docum-context.xml")
@TransactionConfiguration(defaultRollback=false)
@Transactional
public class TestDataPreparator {

	@PersistenceContext(name="docum")
	private EntityManager entityManager;

	private Calendar cal = new GregorianCalendar(
			Calendar.getInstance().get(Calendar.YEAR),
			Calendar.getInstance().get(Calendar.MONTH),
			Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

	private String[] containerNames = new String[] {
			"ZCSU5836992", "ZCSU5132123", "ZCSU5853644", "ZCSU5879274",
			"ZCSU5836626", "ZCSU5846814", "ZCSU5836992", "ZCSU5879398",
			"CGMU5052139", "CGMU5052756", "CGMU5052782", "CGMU5052606",
			"CGMU5053177", "CGMU5052313", "CGMU5052078", "CGMU5052099"};

	private String[] vesselNames = new String[] {
			"Zim Pacific", "Zim India", "Michigan Trader"};
	
	private String[] voyageNumbers = new String[] {
			"13/W", "51/E", "40/E"};

	private String[] supplierNames = new String[] {
			"Qingdao Yunlong Import And Export Co. Ltd.",
			"U.M.S. Industry And Food Ltd.",
			"Jining Sanglong Economic & Trade Co. Ltd."};

	private Double[] cargoWeights = new Double[] {
			12300.0, 22300.0, 24200.0, 20520.0, 19800.0, 25000.0,
			23300.0, 21630.0, 26300.0, 8700.0, };
	
	private String[] invoiceNumbers = new String[]{
			"PP11-652", "PP11-678", "TM11-674", "CL11-675"};

	private String[] orderNumbers = new String[]{
			"12712", "13156", "10892"};

	private String[] tareNames = new String[]{
			"Паллеты", "Поддоны", "Ящики", "Мешки"};

	private String[] cityNames = new String[]{
			"Новороссийск", "Анапа", "Темрюк", "Волгоград"};
	private boolean ourCity = true;
	
	private String[] measureNames = new String[]{
			"шт", "кг", "т"};
	
	@Test
	public void prepareData(){
		List<Supplier> suppliers = prepareSuppliers();
		List<Article> articles = prepareArticles();
		List<Vessel> vessels = prepareVessels();
		List<Voyage> voyages = prepareVoyages(vessels);
		List<City> cities = prepareCities();
		List<Measure> measures  = prepareMesures();
		List<Tare> tares = prepareTares();
		List<Container> containers = prepareContainers(voyages, cities);
		List<Cargo> cargoes = prepareCargoes(articles, suppliers, containers);
		List<BillOfLading> bills = prepareBillOfLadings(containers);
		List<Invoice> invoices = prepareInvoices(containers);
		List<PurchaseOrder> order = prepareOrders(containers);
	}
	
	private List<Voyage> prepareVoyages(List<Vessel> vessels) {
		Calendar pastCal = (Calendar) cal.clone();
		pastCal.add(Calendar.MONTH, -1);
		Calendar futureCal = (Calendar) cal.clone();
		futureCal.add(Calendar.MONTH, 1);
		List<Voyage> result = new ArrayList<Voyage>();
		EntityCounter<Vessel> vesselCounter = new EntityCounter<Vessel>(vessels); 
		EntityCounter<String> nameCounter = new EntityCounter<String>(voyageNumbers); 
		result.add(new Voyage(vesselCounter.next(), nameCounter.next(), pastCal.getTime(), true));
		result.add(new Voyage(vesselCounter.next(), nameCounter.next(), cal.getTime(), true));
		result.add(new Voyage(vesselCounter.next(), nameCounter.next(), futureCal.getTime(), false));
		persist(result);
		return result;
	}

	private List<Vessel> prepareVessels() {
		return prepareDictionary(vesselNames, new EntityConstructor<Vessel>(){
			public Vessel construct(String name) {
				return new Vessel(name);
			}});
	}

	private List<Article> prepareArticles() {
		List<Article> result = new ArrayList<Article>();
		result.add(new Article("Помело свежий", "Помело", "Fresh Pomelo"));
		result.add(new Article("Чеснок свежий", "Чеснок", "Fresh Garlic"));
		result.add(new Article("Яблоки свежие", "Яблоки", "Fresh Apples"));
		result.add(new Article("Грейпфрут свежий", "Грейпфрут", "Fresh Grapefruit"));
		result.add(new Article("Картофель свежий", "Картофель", "Fresh Potatoes"));
		persist(result);
		return result;
	}

	private <T extends IdentifiedEntity> List<T> prepareDictionary(String[] names,
			EntityConstructor<T> constructor) {
		List<T> result = new ArrayList<T>();
		for(String name : names) {
			result.add(constructor.construct(name));
		}
		persist(result);
		return result;
	}
	private static interface EntityConstructor<T extends IdentifiedEntity> {
		public T construct(String name);
	}

	private List<Supplier> prepareSuppliers() {
		return prepareDictionary(supplierNames, new EntityConstructor<Supplier>(){
			public Supplier construct(String name) {
				return new Supplier(name);
			}});
	}

	private List<City> prepareCities() {
		return prepareDictionary(cityNames, new EntityConstructor<City>(){
			public City construct(String name) {
				City city = new City(name, ourCity);
				ourCity = !ourCity;
				return city;
			}});
	}
	
	private List<Measure> prepareMesures() {
		return prepareDictionary(measureNames, new EntityConstructor<Measure>(){
			public Measure construct(String name) {
				return new Measure(name);
			}});
	}
	
	private List<Tare> prepareTares() {
		return prepareDictionary(tareNames, new EntityConstructor<Tare>(){
			public Tare construct(String name) {
				return new Tare(name);
			}});
	}
	
	private List<Container> prepareContainers(List<Voyage> voyages, List<City> cities) {
		final ContainerStateEnumCounter stateCounter = new ContainerStateEnumCounter();
		final EntityCounter<Voyage> voyageCounter = new EntityCounter<Voyage>(voyages);
		final EntityCounter<City> cityCounter = new EntityCounter<City>(cities);

		List<Container> result = new ArrayList<Container>(containerNames.length);
		AlgoUtil.transform(result, Arrays.asList(containerNames),
				new AlgoUtil.TransformFunctor<Container, String>() {
					public Container transform(String name) {
						return new Container(name, stateCounter.next(), voyageCounter.next(),
								cityCounter.next());
					}
		});
		
		persist(result);
		return result;
	}
	
	private List<Cargo> prepareCargoes(List<Article> articles,
			List<Supplier> suppliers, List<Container> containers) {
		EntityCounter<Article> articleCounter = new EntityCounter<Article>(articles);
		EntityCounter<Supplier> supplierCounter = new EntityCounter<Supplier>(suppliers);
		EntityCounter<Container> containerCounter = new EntityCounter<Container>(containers);
		List<Cargo> result = new ArrayList<Cargo>();
		for(Double weight : cargoWeights) {
			result.add(new Cargo(articleCounter.next(), weight, supplierCounter.next(),
					containerCounter.next()));
		}
		persist(result);
		return result;
	}
	
	private List<BillOfLading> prepareBillOfLadings(List<Container> containers){
		List<BillOfLading> result = new ArrayList<BillOfLading>();
		EntityCounter<Container> containerCounter = new EntityCounter<Container>(containers);

		result.add(new BillOfLading("ZIMUASH032310"));
		result.add(new BillOfLading("ZIMUHFA00322361"));
		EntityCounter<BillOfLading> billCounter = new EntityCounter<BillOfLading>(result);
		
		for(int i=0; i<containers.size(); i++){
			Container container = containerCounter.next();
			BillOfLading bill = billCounter.next();
			bill.getContainers().add(container);
			container.getBillOfLadings().add(bill);
		}
		//TODO: Может ли быть два коносамента на один контейнер?
		Container container = containers.get(1);
		BillOfLading bill = result.get(0);
		bill.getContainers().add(container);
		container.getBillOfLadings().add(bill);

		persist(result);
		return result;
	}
	
	private List<Invoice> prepareInvoices(List<Container> containers){
		List<Invoice> result = new ArrayList<Invoice>();
		
		EntityCounter<Container> containerCounter = new EntityCounter<Container>(containers);

		for(String number : invoiceNumbers) {
			Invoice invoice = new Invoice(number);
			Container container = containerCounter.next();
			invoice.getContainers().add(container);
			container.getInvoices().add(invoice);
			container = containerCounter.next();
			invoice.getContainers().add(container);
			container.getInvoices().add(invoice);
			result.add(invoice);
		}
		persist(result);
		return result;
	}

	private List<PurchaseOrder> prepareOrders(List<Container> containers){
		List<PurchaseOrder> result = new ArrayList<PurchaseOrder>();
		
		EntityCounter<Container> containerCounter = new EntityCounter<Container>(containers);

		for(String number : orderNumbers) {
			PurchaseOrder order = new PurchaseOrder(number);
			Container container = containerCounter.next();
			order.getContainers().add(container);
			container.getOrders().add(order);
			container = containerCounter.next();
			order.getContainers().add(container);
			container.getOrders().add(order);
			result.add(order);
		}
		persist(result);
		return result;
	}

	
	
	
	
	private <T extends IdentifiedEntity> void persist(Collection<T> entities) {
		for(T entity : entities){
			entityManager.persist(entity);
		}
	}

	public static class EntityCounter<T> {
		private Collection<T> entities;
		private Iterator<T> it;

		public EntityCounter(Collection<T> entities) {
			this.entities = entities;
			it = this.entities.iterator();
		}

		public EntityCounter(T[] entities) {
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

	public static class EnumCounter<E extends Enum<E>> extends EntityCounter<E> {
		public EnumCounter(E...enums) {
			super(Arrays.asList(enums));
		}
		public EnumCounter(E[] enums, E...excluded) {
			super(new Helper<E>(enums, excluded).getList());
		}
		private static class Helper<E extends Enum<E>> {
			private List<E> enums; 
			public Helper(E[] enums, E[] excluded) {
				this.enums = new ArrayList<E>(Arrays.asList(enums));
				this.enums.removeAll(Arrays.asList(excluded));
			}
			public List<E> getList() {
				return enums;
			}
		}
	}

	public static class ContainerStateEnumCounter extends EnumCounter<ContainerStateEnum> {
		ContainerStateEnumCounter() {
			super(ContainerStateEnum.values(), ContainerStateEnum.ABANDONED);
		}
	}
}
