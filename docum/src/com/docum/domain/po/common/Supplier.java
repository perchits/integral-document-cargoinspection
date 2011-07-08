package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
public class Supplier extends IdentifiedEntity {

	private static final long serialVersionUID = 7918790765968857071L;

	@ManyToOne
	private Company company;

	public Supplier() {
	}

	public Supplier(Company company) {
		this.company = company;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Supplier)) {
			return false;
		}

		return EqualsHelper.equals(getId(), ((Supplier) obj).getId());
	}

	public int hashCode() {
		return HashCodeHelper.hashCode(getId());
	}
	
	@Override
	public String toString(){		
		return company != null ? company.getName() : "";
	}

}