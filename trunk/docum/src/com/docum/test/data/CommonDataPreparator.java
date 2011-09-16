package com.docum.test.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.docum.domain.po.common.ActualCargoCondition;
import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.BillOfLading;
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.City;
import com.docum.domain.po.common.Company;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Customer;
import com.docum.domain.po.common.DeclaredCargoCondition;
import com.docum.domain.po.common.Inspection;
import com.docum.domain.po.common.Invoice;
import com.docum.domain.po.common.Measure;
import com.docum.domain.po.common.Port;
import com.docum.domain.po.common.PurchaseOrder;
import com.docum.domain.po.common.Supplier;
import com.docum.domain.po.common.SurveyPlace;
import com.docum.domain.po.common.Surveyor;
import com.docum.domain.po.common.Vessel;
import com.docum.domain.po.common.Voyage;
import com.docum.test.data.TestDataPreparator.ContainerStateEnumCounter;
import com.docum.util.AlgoUtil;

public class CommonDataPreparator extends AbstractDataPreparator {

	public static final Calendar cal = new GregorianCalendar(
			Calendar.getInstance().get(Calendar.YEAR),
			Calendar.getInstance().get(Calendar.MONTH),
			Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

	public static final String[] containerNames = new String[] {
			"ZCSU5836992", "ZCSU5132123", "ZCSU5853644", "ZCSU5879274",
			"ZCSU5836626", "ZCSU5846814", "ZCSU5845256", "ZCSU5879398",
			"CGMU5052139", "CGMU5052756", "CGMU5052782", "CGMU5052606",
			"CGMU5053177", "CGMU5052313", "CGMU5052078", "CGMU5052099"
			};

	public static final String[] vesselNames = new String[] {
			"Zim Pacific", "Zim India", "Michigan Trader", "Мerkur Beach"};
	
	public static final String[] voyageNumbers = new String[] {
			"13/W", "51/E", "40/E", "1103"};

	public static final String[] invoiceNumbers = new String[]{
			"PP11-652", "PP11-678", "TM11-674", "CL11-675", "TM11-689"};

	public static final String[] orderNumbers = new String[]{
			"12712", "13156", "10892", "11242"};

	public static final String[] blNumbers = new String[]{
			"ZIMUASH032310", "ZIMUHFA00322361", "GHUIR0349578", "GHUIR0304874"};

	public static final String[][] cityNames = new String[][]{
			{"Новороссийск", "Novorossiysk"}, {"Анапа", "Anapa"}, {"Темрюк", "Temryuk"}, {"Волгоград", "Volgograd"}};
	public static boolean ourCity = true;
	
	public static final String[][] measureNames = new String[][]{
			{"Паллеты, обернутые пленкой", "Wrapped pallets"},
			{"Ящики деревянные без верха", "Topless wooden boxes"}, 
			{"Мешки полиэтиленовые", "Polyethylene bags"},
			{"Общий вес в килограммах", "Total weight"}};
	
	public static final String[][] companyNames = new String[][] {
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

	public static final String[] sealNames = new String[] {
			"ZZD266900/RH40", "AHL2296968", "AHL2296969", "AHL2296970",
			"ZZB353945/RH40", "268559/RH40", "268558/RH40", "268560/RH40",
			"ZZD266901/RH40", "AHL2296969", "AHL2296980", "AHL2296981",
			"ZZB353955/RH40", "268569/RH40", "268568/RH40", "268570/RH40"
			};

	public static final String[][] portNames = new String[][] {
			{"Стамбул", "Istanbul"},
			{"Роттердам", "Rotterdam"},
			{"Гамбург", "Hamburg"},
			{"Нью-Йорк", "New York"},
			{"Новороссийск", "Novorossiysk"}};
	
	public static final String[][] surveyPlaceNames = new String[][] {
			{"Контейнерный терминал ОАО «Новороссийский лесной порт»", "Container terminal «NLE»"},
			{"Контейнерный терминал ОАО «Новороссийский торговый порт»", "Container terminal «NMTP»"}};
	
	public static final String[][] surveyorNames = new String[][] {
			{"Дмитриева А.", "Dmitrieva А."},
			{"Петров Д.", "Petrov D."}};
	
	public static final Double[] temperatures = new Double[] { 4.2, 3.0, 12.5, 6.1,
		8.9, 4.7, 11.0, 9.3, 6.8, 7.0 };

	public CommonDataPreparator() {
		super();
	}

	public CommonDataPreparator(TestDataPersister persister) {
		super(persister);
	}

	public List<Port> preparePorts() {
		return TestDataPrepareUtil.prepareDictionary(persister, portNames,
				new TestDataEntityConstructor<Port, String[]>() {
					public Port construct(String[] names) {
						return new Port(names[0], names[1]);
					}
				});
	}

	public List<Voyage> prepareVoyages(List<Vessel> vessels) {
		Calendar pastCal = (Calendar) cal.clone();
		pastCal.add(Calendar.MONTH, -2);
		Calendar futureCal = (Calendar) cal.clone();
		futureCal.add(Calendar.MONTH, 1);
		List<Voyage> result = new ArrayList<Voyage>();
		TestDataEntityCounter<Vessel> vesselCounter = new TestDataEntityCounter<Vessel>(vessels); 
		TestDataEntityCounter<String> nameCounter = new TestDataEntityCounter<String>(voyageNumbers); 
		result.add(new Voyage(vesselCounter.next(), nameCounter.next(), pastCal.getTime(), true));
		pastCal.add(Calendar.MONTH, 1);
		result.add(new Voyage(vesselCounter.next(), nameCounter.next(), pastCal.getTime(), true));
		result.add(new Voyage(vesselCounter.next(), nameCounter.next(), cal.getTime(), true));
		result.add(new Voyage(vesselCounter.next(), nameCounter.next(), futureCal.getTime(), false));
		persister.persist(result);
		return result;
	}

	public List<Vessel> prepareVessels() {
		return TestDataPrepareUtil.prepareDictionary(persister, vesselNames,
				new TestDataEntityConstructor<Vessel, String>() {
					public Vessel construct(String name) {
						return new Vessel(name);
					}
				});
	}

	public List<Company> prepareCompanies(){
		List<Company> result = new ArrayList<Company>();
		for(String c[] : companyNames) { 
			result.add(new Company(c[0],c[1],c[2],c[3],c[4],c[5]));
		}
		persister.persist(result);
		return result;
	}
	
	public List<Supplier> prepareSuppliers(List<Company> companies) {
		List<Supplier> result = new ArrayList<Supplier>();
		for(int i = 0; i < 3 ; i++) {
			result.add(new Supplier(companies.get(i)));
		}
		persister.persist(result);
		return result;
	}
	
	public List<Customer> prepareCustomers(List<Company> companies) {
		List<Customer> result = new ArrayList<Customer>();		
		result.add(new Customer(companies.get(3)));		
		persister.persist(result);
		return result;
	}

	public List<SurveyPlace> prepareSurveyPlaces() {
		List<SurveyPlace> result = new ArrayList<SurveyPlace>();
		for (String place[] : surveyPlaceNames) { 
			result.add(new SurveyPlace(place[1],place[0]));
		}
		persister.persist(result);
		return result;
	}
	
	public List<Surveyor> prepareSurveyors() {
		List<Surveyor> result = new ArrayList<Surveyor>();
		for (String surveyor[] : surveyorNames) { 
			result.add(new Surveyor(surveyor[0],surveyor[1]));
		}
		persister.persist(result);
		return result;
	}
	
	public List<City> prepareCities() {
		return TestDataPrepareUtil.prepareDictionary(persister, cityNames,
				new TestDataEntityConstructor<City, String[]>() {
					public City construct(String[] names) {
						City city = new City(names[0], names[1], ourCity);
						ourCity = !ourCity;
						return city;
					}
				});
	}
	
	public List<Inspection> prepareInspections(List<Container> containers,
			List<SurveyPlace> surveyPlaces, List<Surveyor> surveyors) {
		final TestDataEntityCounter<SurveyPlace> surveyPlaceCounter =
			new TestDataEntityCounter<SurveyPlace>(surveyPlaces);
		final TestDataEntityCounter<Surveyor> surveyorCounter =
			new TestDataEntityCounter<Surveyor>(surveyors);
		final TestDataEntityCounter<Double> temperatureCounter =
			new TestDataEntityCounter<Double>(temperatures);
		List<Inspection> result = new ArrayList<Inspection>();		
		for (int i=0; i<containers.size(); i+=3) {
			Container container = containers.get(i);
			container.setActualCondition(prepareActualCondition(container, temperatureCounter));
			result.add(prepareInspection(container, surveyPlaceCounter.next(),
					surveyorCounter.next()));
		}
		persister.persist(result);
		return null;		
	}

	public Inspection prepareInspection(Container container, SurveyPlace surveyPlace,
			Surveyor surveyor) {
		Inspection inspection = new Inspection(container);
		inspection.setActualSeal("ML-1199363");
		inspection.setSurveyRequestDate(new Date(2001, 9, 1));
		inspection.setSurveyDate(new Date(2001, 9, 15));
		inspection.setSurveyPlace(surveyPlace);
		inspection.setUnloadingPlace("На склад");
		inspection.setUnloadingPlaceEng("To warehouse");
		inspection.setPackageType(null);
		inspection.setPackageTypeEng(null);
		inspection.setPackageForming("Каждая паллета содержащая ящики с грузом была закреплена " +
				"угловым креплением и пластиковыми ремнями.");
		inspection.setPackageFormingEng("Every pallet, containing p.cases with cargo, was fixed " +
				"by angular fastening plastic belts");
		inspection.setPackageState("В ходе инспекции повреждения упаковки замечено не было.");
		inspection.setPackageStateEng("During inspection damage of packing it has not " +
				"been noticed.");
		inspection.setPackageToSurvey("2350,5619,3894");
		inspection.setPackageToSurveyEng("2350,5619,3894");
		inspection.setSurveyorConclusion("На момент осмотра груза найдено пораженных гнилью" +
				" плодов -0,2%, осыпавшиеся ягоды – 1,4%. Незначительные солнечные ожоги," +
				" затрагивающие только лишь кожицу -1,7%.");
		inspection.setSurveyorConclusionEng("As of cargo survey it was find fruits affected by" +
				" rot -0,2%, loose berries – 1,4%. ");
		inspection.setSurveyor(surveyor);
		
		container.setInspection(inspection);
		return inspection;
	}
	
	public List<Measure> prepareMesures() {
		return TestDataPrepareUtil.prepareDictionary(persister, measureNames,
				new TestDataEntityConstructor<Measure, String[]>() {
					public Measure construct(String[] names) {
						return new Measure(names[0], names[1]);
					}
				});
	}
	
	public List<Container> prepareContainers(List<Voyage> voyages, List<City> cities,
			List<Port> ports) {
		final ContainerStateEnumCounter stateCounter = new ContainerStateEnumCounter();
		final TestDataEntityCounter<Voyage> voyageCounter = new TestDataEntityCounter<Voyage>(voyages);
		final TestDataEntityCounter<City> cityCounter = new TestDataEntityCounter<City>(cities);
		final TestDataEntityCounter<Port> portCounter = new TestDataEntityCounter<Port>(ports);
		final TestDataEntityCounter<String> sealCounter = new TestDataEntityCounter<String>(sealNames);
		final TestDataEntityCounter<Double> temperatureCounter = new TestDataEntityCounter<Double>(temperatures);
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
						result.setDeclaredCondition(prepareDeclaredCondition(result, temperatureCounter));
						return result;
					}
		});
		
		persister.persist(result);

		return result;
	}
	
	public List<BillOfLading> prepareBillOfLadings(List<Voyage> voyages, List<Container> containers){
		List<BillOfLading> result = new ArrayList<BillOfLading>();
		TestDataEntityCounter<Voyage> voyageCounter = new TestDataEntityCounter<Voyage>(voyages);
		for(String number : blNumbers) {
			BillOfLading bl = new BillOfLading(voyageCounter.next(), number);
			result.add(bl);
		}
		Map<Voyage, TestDataEntityCounter<BillOfLading>> billCounterByVoyageMap =
			makeCounterByKeyMap(result, new KeyAccessor<Voyage, BillOfLading>(){
				@Override
				public Voyage getKey(BillOfLading obj) {
					return obj.getVoyage();
				}});
		for(Container container : containers){
			BillOfLading bill = billCounterByVoyageMap.get(container.getVoyage()).next();
			bill.getContainers().add(container);
			container.getBillOfLadings().add(bill);
		}
		persister.persist(result);
		return result;
	}

	public interface KeyAccessor<K, T> {
		public K getKey(T obj);
	}

	public <K, T> Map<K, TestDataEntityCounter<T>> makeCounterByKeyMap(List<T> objects, KeyAccessor<K, T> keyAccessor) {
		Map<K, Set<T>> objectsByKeyMap =	makeObjectsByKeyMap(objects, keyAccessor);
		Map<K, TestDataEntityCounter<T>> result = new HashMap<K, TestDataEntityCounter<T>>();
		for(Entry<K, Set<T>> entry : objectsByKeyMap.entrySet()) {
			result.put(entry.getKey(),	new TestDataEntityCounter<T>(entry.getValue()));
		}
		return result;
	}

	public <K, T> Map<K, Set<T>> makeObjectsByKeyMap(List<T> objects, KeyAccessor<K, T> keyAccessor) {
		Map<K, Set<T>> result = new HashMap<K, Set<T>>();
		for(T obj : objects) {
			K key = keyAccessor.getKey(obj);
			Set<T> tmp = result.get(key);
			if(tmp == null) {
				tmp = new HashSet<T>();
				result.put(key, tmp);
			}
			tmp.add(obj);
		}
		return result;
	}
	
	public List<Invoice> prepareInvoices(List<Voyage> voyages, List<Container> containers){
		List<Invoice> result = new ArrayList<Invoice>();
		TestDataEntityCounter<Voyage> voyageCounter = new TestDataEntityCounter<Voyage>(voyages);
		for(String number : invoiceNumbers) {
			Invoice invoice = new Invoice(voyageCounter.next(), number);
			result.add(invoice);
		}
		Map<Voyage, TestDataEntityCounter<Invoice>> invoiceCounterByVoyageMap =
			makeCounterByKeyMap(result, new KeyAccessor<Voyage, Invoice>(){
				@Override
				public Voyage getKey(Invoice obj) {
					return obj.getVoyage();
				}});
		for(Container container : containers) {
			Invoice invoice = invoiceCounterByVoyageMap.get(container.getVoyage()).next();
			invoice.getContainers().add(container);
			container.getInvoices().add(invoice);
		}
		persister.persist(result);
		return result;
	}

	public List<PurchaseOrder> prepareOrders(List<Voyage> voyages, List<Container> containers){
		List<PurchaseOrder> result = new ArrayList<PurchaseOrder>();
		TestDataEntityCounter<Voyage> voyageCounter = new TestDataEntityCounter<Voyage>(voyages);
		for(String number : orderNumbers) {
			PurchaseOrder invoice = new PurchaseOrder(voyageCounter.next(), number);
			result.add(invoice);
		}
		Map<Voyage, TestDataEntityCounter<PurchaseOrder>> orderCounterByVoyageMap =
			makeCounterByKeyMap(result, new KeyAccessor<Voyage, PurchaseOrder>(){
				@Override
				public Voyage getKey(PurchaseOrder obj) {
					return obj.getVoyage();
				}});
		for(Container container : containers) {
			PurchaseOrder order = orderCounterByVoyageMap.get(container.getVoyage()).next();
			order.getContainers().add(container);
			container.getOrders().add(order);
		}
		persister.persist(result);
		return result;
	}

	public DeclaredCargoCondition prepareDeclaredCondition(Container container,
			TestDataEntityCounter<Double> temperatureCounter) {
		DeclaredCargoCondition condition = container.getDeclaredCondition();
		condition.setMinTemperature(temperatureCounter.next());
		condition.setMaxTemperature(temperatureCounter.next());
		return condition;
	}

	public ActualCargoCondition prepareActualCondition(Container container,
			TestDataEntityCounter<Double> temperatureCounter) {
		ActualCargoCondition condition = container.getActualCondition();
		condition.setTemperature(temperatureCounter.next());
		return condition;
	}

}
