package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.docum.domain.po.IdentifiedEntity;

@Entity
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
