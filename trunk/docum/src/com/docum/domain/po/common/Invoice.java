package com.docum.domain.po.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.docum.dao.InvoiceDao;
import com.docum.domain.po.IdentifiedEntity;

@Entity
@NamedQueries(
		@NamedQuery(
				name = InvoiceDao.GET_INVOICES_BY_VOYAGE_QUERY,
				query = "SELECT DISTINCT inv FROM Invoice inv JOIN inv.containers c " +
					"WHERE c.voyage.id=:voyageId"
		)
)
public class Invoice extends IdentifiedEntity{
	private static final long serialVersionUID = 4144517745472469185L;

	private String number;
	@ManyToMany
	private List<Container> containers = new ArrayList<Container>();

	public Invoice() {
		super();
	}

	public Invoice(String number) {
		super();
		this.number = number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumber() {
		return number;
	}

	public void setContainers(List<Container> containers) {
		this.containers = containers;
	}

	public List<Container> getContainers() {
		return containers;
	}
	
}
