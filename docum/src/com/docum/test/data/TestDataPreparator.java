package com.docum.test.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
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
import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.BillOfLading;
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.City;
import com.docum.domain.po.common.Company;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Customer;
import com.docum.domain.po.common.Invoice;
import com.docum.domain.po.common.Measure;
import com.docum.domain.po.common.Port;
import com.docum.domain.po.common.PurchaseOrder;
import com.docum.domain.po.common.Supplier;
import com.docum.domain.po.common.Vessel;
import com.docum.domain.po.common.Voyage;
import com.docum.util.AlgoUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/docum-context.xml")
@TransactionConfiguration(defaultRollback=false)
@Transactional
public class TestDataPreparator implements TestDataPersister {

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

	private String[] invoiceNumbers = new String[]{
			"PP11-652", "PP11-678", "TM11-674", "CL11-675"};

	private String[] orderNumbers = new String[]{
			"12712", "13156", "10892"};

	private String[] cityNames = new String[]{
			"Новороссийск", "Анапа", "Темрюк", "Волгоград"};
	private boolean ourCity = true;
	
	private String[] measureNames = new String[]{
			"Паллеты, обернутые пленкой", "Ящики деревянные без верха", "Мешки полиэтиленовые",
			"Общий вес в килограммах"};
	
	private String[][] companyNames = new String[][] {
			{"Кингдао Юнлонг","Кингдао",
				"Qingdao Yunlong Import And Export Co. Ltd.","Qingdao Yunlong",
				"Адрес", "Address"				
			},
			{"Ю. М. С Индастри","Ю. М. С.", 
				"U.M.S. Industry And Food Ltd.","U.M.S.",
				"Адрес", "Address"
			},			
			{"Джининг Санглонг Экономик","Джининг Санглонг",
				"Jining Sanglong Economic & Trade Co. Ltd.", "Jining Sanglong",
				"Адрес", "Address"
			},
			{"ЗAO «Тандер»","ЗAO «Тандер»",
				"CJSC «Tander»","CJSC «Tander»",
				"Россия 350002 Краснодар ул. Леваневского 185", "185 Levanevskogo Street Krasnodar, 350002 Russia"
			}
			};

	private String[] sealNames = new String[] {
			"ZZD266900/RH40", "AHL2296968", "AHL2296969", "AHL2296970",
			"ZZB353945/RH40", "268559/RH40", "268558/RH40", "268560/RH40",
			"ZZD266901/RH40", "AHL2296969", "AHL2296980", "AHL2296981",
			"ZZB353955/RH40", "268569/RH40", "268568/RH40", "268570/RH40"
			};

	private String[][] portNames = new String[][] {
			{"Стамбул", "Istanbul"},
			{"Роттердам", "Rotterdam"},
			{"Гамбург", "Hamburg"},
			{"Нью-Йорк", "New York"},
			{"Новороссийск", "Novorossiysk"}};
	
	@SuppressWarnings(value="unused")
	@Test
	public void prepareData(){
		List<Company> companies = prepareCompanies();
		List<Supplier> suppliers = prepareSuppliers(companies);
		List<Customer> customers = prepareCustomers(companies);
		List<Article> articles = ArticleDataPreparator.prepareArticles(this);
		List<Vessel> vessels = prepareVessels();
		List<Voyage> voyages = prepareVoyages(vessels);
		List<City> cities = prepareCities();
		List<Measure> measures  = prepareMesures();
		List<Port> ports = preparePorts();
		List<Container> containers = prepareContainers(voyages, cities, ports);
		List<Cargo> cargoes = CargoDataPreparator.prepareCargoes(this, articles, suppliers,
				containers, measures);
		List<BillOfLading> bills = prepareBillOfLadings(containers);
		List<Invoice> invoices = prepareInvoices(containers);
		List<PurchaseOrder> order = prepareOrders(containers);
	}
	
	private List<Port> preparePorts() {
		return prepareDictionary(portNames, new EntityConstructor<Port, String[]>(){
			public Port construct(String[] names) {
				return new Port(names[0], names[1]);
			}});
	}

	private List<Voyage> prepareVoyages(List<Vessel> vessels) {
		Calendar pastCal = (Calendar) cal.clone();
		pastCal.add(Calendar.MONTH, -1);
		Calendar futureCal = (Calendar) cal.clone();
		futureCal.add(Calendar.MONTH, 1);
		List<Voyage> result = new ArrayList<Voyage>();
		TestDataEntityCounter<Vessel> vesselCounter = new TestDataEntityCounter<Vessel>(vessels); 
		TestDataEntityCounter<String> nameCounter = new TestDataEntityCounter<String>(voyageNumbers); 
		result.add(new Voyage(vesselCounter.next(), nameCounter.next(), pastCal.getTime(), true));
		result.add(new Voyage(vesselCounter.next(), nameCounter.next(), cal.getTime(), true));
		result.add(new Voyage(vesselCounter.next(), nameCounter.next(), futureCal.getTime(), false));
		persist(result);
		return result;
	}

	private List<Vessel> prepareVessels() {
		return prepareDictionary(vesselNames, new EntityConstructor<Vessel, String>(){
			public Vessel construct(String name) {
				return new Vessel(name);
			}});
	}

	

	private <T extends IdentifiedEntity, S> List<T> prepareDictionary(S[] names,
			EntityConstructor<T, S> constructor) {
		List<T> result = new ArrayList<T>();
		for(S name : names) {
			result.add(constructor.construct(name));
		}
		persist(result);
		return result;
	}
	private static interface EntityConstructor<T extends IdentifiedEntity, S> {
		public T construct(S name);
	}

    private List<Company> prepareCompanies(){
		List<Company> result = new ArrayList<Company>();
		for(String c[] : companyNames) { 
			result.add(new Company(c[0],c[1],c[2],c[3],c[4],c[5]));
		}
		persist(result);
		return result;
	}
	
	private List<Supplier> prepareSuppliers(List<Company> companies) {
		List<Supplier> result = new ArrayList<Supplier>();
		for(int i = 0; i < 3 ; i++) {
			result.add(new Supplier(companies.get(i)));
		}
		persist(result);
		return result;
	}
	
	private List<Customer> prepareCustomers(List<Company> companies) {
		List<Customer> result = new ArrayList<Customer>();		
		result.add(new Customer(companies.get(3)));		
		persist(result);
		return result;
	}
	private List<City> prepareCities() {
		return prepareDictionary(cityNames, new EntityConstructor<City, String>(){
			public City construct(String name) {
				City city = new City(name, ourCity);
				ourCity = !ourCity;
				return city;
			}});
	}
	
	private List<Measure> prepareMesures() {
		return prepareDictionary(measureNames, new EntityConstructor<Measure, String>(){
			public Measure construct(String name) {
				return new Measure(name);
			}});
	}
	
	private List<Container> prepareContainers(List<Voyage> voyages, List<City> cities,
			List<Port> ports) {
		final ContainerStateEnumCounter stateCounter = new ContainerStateEnumCounter();
		final TestDataEntityCounter<Voyage> voyageCounter = new TestDataEntityCounter<Voyage>(voyages);
		final TestDataEntityCounter<City> cityCounter = new TestDataEntityCounter<City>(cities);
		final TestDataEntityCounter<Port> portCounter = new TestDataEntityCounter<Port>(ports);
		final TestDataEntityCounter<String> sealCounter = new TestDataEntityCounter<String>(sealNames);
		final Calendar pastCal = (Calendar) cal.clone();
		pastCal.add(Calendar.MONTH, -6);

		List<Container> result = new ArrayList<Container>(containerNames.length);
		AlgoUtil.transform(result, Arrays.asList(containerNames),
				new AlgoUtil.TransformFunctor<Container, String>() {
					public Container transform(String name) {
						Container result = new Container(name, stateCounter.next(), voyageCounter.next(),
								cityCounter.next());
						result.setLoadingPort(portCounter.next());
						pastCal.add(Calendar.DAY_OF_MONTH, 5);
						result.setLoadingDate(cal.getTime());
						result.setDischargePort(portCounter.next());
						pastCal.add(Calendar.DAY_OF_MONTH, 5);
						result.setDischargeDate(cal.getTime());
						result.setDeclaredSeal(sealCounter.next());
						result.setActualSeal(result.getDeclaredSeal());
						return result;
					}
		});
		
		persist(result);
		return result;
	}
	
	private List<BillOfLading> prepareBillOfLadings(List<Container> containers){
		List<BillOfLading> result = new ArrayList<BillOfLading>();
		TestDataEntityCounter<Container> containerCounter = new TestDataEntityCounter<Container>(containers);

		result.add(new BillOfLading("ZIMUASH032310"));
		result.add(new BillOfLading("ZIMUHFA00322361"));
		TestDataEntityCounter<BillOfLading> billCounter = new TestDataEntityCounter<BillOfLading>(result);
		
		for(int i=0; i<containers.size(); i++){
			Container container = containerCounter.next();
			BillOfLading bill = billCounter.next();
			bill.getContainers().add(container);
			container.getBillOfLadings().add(bill);
		}
		Container container = containers.get(1);
		BillOfLading bill = result.get(0);
		bill.getContainers().add(container);
		container.getBillOfLadings().add(bill);

		persist(result);
		return result;
	}
	
	private List<Invoice> prepareInvoices(List<Container> containers){
		List<Invoice> result = new ArrayList<Invoice>();
		for(String number : invoiceNumbers) {
			Invoice invoice = new Invoice(number);
			result.add(invoice);
		}
		TestDataEntityCounter<Invoice> invoiceCounter = new TestDataEntityCounter<Invoice>(result);
		for(Container container : containers) {
			Invoice invoice = invoiceCounter.next();
			invoice.getContainers().add(container);
			container.getInvoices().add(invoice);
		}
		persist(result);
		return result;
	}

	private List<PurchaseOrder> prepareOrders(List<Container> containers){
		List<PurchaseOrder> result = new ArrayList<PurchaseOrder>();
		for(String number : orderNumbers) {
			PurchaseOrder invoice = new PurchaseOrder(number);
			result.add(invoice);
		}
		TestDataEntityCounter<PurchaseOrder> orderCounter = new TestDataEntityCounter<PurchaseOrder>(result);
		for(Container container : containers) {
			PurchaseOrder order = orderCounter.next();
			order.getContainers().add(container);
			container.getOrders().add(order);
		}
		persist(result);
		return result;
	}

	
	
	
	@Override
	public <T extends IdentifiedEntity> void persist(T entity) {
		entityManager.persist(entity);
	}

	@Override
	public <T extends IdentifiedEntity> void persist(Collection<T> entities) {
		for(T entity : entities){
			persist(entity);
		}
	}

	public static class ContainerStateEnumCounter extends TestDataEnumCounter<ContainerStateEnum> {
		ContainerStateEnumCounter() {
			super(ContainerStateEnum.values(), ContainerStateEnum.ABANDONED);
		}
	}
}
