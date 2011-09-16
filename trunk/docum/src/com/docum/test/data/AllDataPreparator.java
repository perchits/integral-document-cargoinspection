package com.docum.test.data;

import java.util.List;

import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.BillOfLading;
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.City;
import com.docum.domain.po.common.Company;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Customer;
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

public class AllDataPreparator extends AbstractDataPreparator {

	private List<Company> companies;
	private List<Supplier> suppliers;
	private List<Customer> customers;
	private List<Article> articles;
	private List<Vessel> vessels;
	private List<Voyage> voyages;
	private List<City> cities;
	private List<Measure> measures;
	private List<Port> ports;
	private List<SurveyPlace> surveyPlaces;
	private List<Surveyor> surveyors;
	private List<Container> containers;
	private List<Inspection> inspections;
	private List<Cargo> cargoes;
	private List<BillOfLading> bills;
	private List<Invoice> invoices;
	private List<PurchaseOrder> order;

	public AllDataPreparator() {
		super();
	}

	public AllDataPreparator(TestDataPersister persister) {
		super(persister);
	}

	public void prepareAllData(){
		CommonDataPreparator commonPreparator = new CommonDataPreparator(persister);
		ArticleDataPreparator articlePreparator = new ArticleDataPreparator(persister);
		CargoDataPreparator cargoPreparator = new CargoDataPreparator(persister);
		companies = commonPreparator.prepareCompanies();
		suppliers = commonPreparator.prepareSuppliers(companies);
		customers = commonPreparator.prepareCustomers(companies);
		articles = articlePreparator.prepareArticles();
		vessels = commonPreparator.prepareVessels();
		voyages = commonPreparator.prepareVoyages(vessels);
		cities = commonPreparator.prepareCities();
		measures = commonPreparator.prepareMesures();
		ports = commonPreparator.preparePorts();
		surveyPlaces = commonPreparator.prepareSurveyPlaces();
		surveyors = commonPreparator.prepareSurveyors();
		containers = commonPreparator.prepareContainers(voyages, cities, ports);
		inspections = commonPreparator.prepareInspections(containers,
				surveyPlaces, surveyors);
		cargoes = cargoPreparator.prepareCargoes(articles, suppliers,
				containers, measures);
		bills = commonPreparator.prepareBillOfLadings(voyages, containers);
		invoices = commonPreparator.prepareInvoices(voyages, containers);
		order = commonPreparator.prepareOrders(voyages, containers);
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public List<Supplier> getSuppliers() {
		return suppliers;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public List<Vessel> getVessels() {
		return vessels;
	}

	public List<Voyage> getVoyages() {
		return voyages;
	}

	public List<City> getCities() {
		return cities;
	}

	public List<Measure> getMeasures() {
		return measures;
	}

	public List<Port> getPorts() {
		return ports;
	}

	public List<SurveyPlace> getSurveyPlaces() {
		return surveyPlaces;
	}

	public List<Surveyor> getSurveyors() {
		return surveyors;
	}

	public List<Container> getContainers() {
		return containers;
	}

	public List<Inspection> getInspections() {
		return inspections;
	}

	public List<Cargo> getCargoes() {
		return cargoes;
	}

	public List<BillOfLading> getBills() {
		return bills;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public List<PurchaseOrder> getOrder() {
		return order;
	}
}
