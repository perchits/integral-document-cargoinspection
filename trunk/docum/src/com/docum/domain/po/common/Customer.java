package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.docum.dao.CustomerDao;
import com.docum.domain.po.IdentifiedEntity;

@Entity
@NamedQueries({
	@NamedQuery(
			name = CustomerDao.GET_DEFAULT_CUSTOMER,
			query = "select c from Customer c " +
					"where c.id = (select min(id) from Customer)"
	)
})
public class Customer extends IdentifiedEntity {

	private static final long serialVersionUID = 5006930255152064933L;
	
	@ManyToOne
	private Company company;

	public Customer() {
	}

	public Customer(Company company) {		
		this.company = company;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	@Override
	public String toString() {
		return this.company != null ? this.company.getName() : null;
	}

}
