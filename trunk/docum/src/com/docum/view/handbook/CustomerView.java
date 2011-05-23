package com.docum.view.handbook;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Company;
import com.docum.domain.po.common.Customer;

@ManagedBean(name = "customerBean")
@SessionScoped
public class CustomerView extends BaseView implements Serializable {
	private static final long serialVersionUID = 7576706351479590355L;
		
	private static final String sign = "Клиент";
	private Customer customer = new Customer();
	private List<Company> companies;

	public List<Company> getCompanies() {
		if (companies == null) {
			companies = getBaseService().getAll(Company.class, null);
		}
		return companies;
	}

	public Company getSelectedCompany() {
		return customer == null ? null : customer.getCompany();
	}

	public void setSelectedCompany(Company selectedCompany) {
		if (customer != null) {
			customer.setCompany(selectedCompany);
		}
	}

	@Override
	public void newObject() {
		super.newObject();
		customer = new Customer();
	}

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBase() {
		return getSelectedCompany().getName();
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return customer != null ? this.customer : new Customer();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
