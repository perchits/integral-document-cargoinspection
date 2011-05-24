package com.docum.view.handbook;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Company;
import com.docum.domain.po.common.Customer;

@Controller("customerBean")
@Scope("session")
public class CustomerView extends BaseView {
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
