package com.docum.domain.po.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.docum.dao.InvoiceDao;
import com.docum.domain.po.IdentifiedEntity;

@Entity
@NamedQueries({
		@NamedQuery(
				name = InvoiceDao.GET_INVOICES_BY_VOYAGE_QUERY,
				query = "SELECT DISTINCT inv FROM Invoice inv JOIN inv.containers c " +
					"WHERE c.voyage.id=:voyageId " +
					"ORDER BY inv.id"
		),
		@NamedQuery(
				name = InvoiceDao.GET_INVOICES_BY_PURCHASE_ORDER_QUERY,
				query = "SELECT DISTINCT inv FROM Invoice inv JOIN inv.containers c " +
					"JOIN c.orders o " + 
					"WHERE o.id=:orderId"
		),
		@NamedQuery(
				name = InvoiceDao.GET_INVOICES_BY_BILL_OF_LADING_QUERY,
				query = "SELECT DISTINCT inv FROM Invoice inv JOIN inv.containers c " +
					"JOIN c.billOfLadings b " + 
					"WHERE b.id=:billOfLadingId"
		)
})
public class Invoice extends IdentifiedEntity{
	private static final long serialVersionUID = 4144517745472469185L;

	private String number;

	@ManyToOne(optional=false)
	private Voyage voyage;

	@ManyToMany
	private List<Container> containers = new ArrayList<Container>();

	public Invoice() {
		super();
	}

	public Invoice(Voyage voyage, String number) {
		super();
		this.voyage = voyage;
		this.number = number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumber() {
		return number;
	}

	public Voyage getVoyage() {
		return voyage;
	}

	public void setVoyage(Voyage voyage) {
		this.voyage = voyage;
	}

	public void setContainers(List<Container> containers) {
		this.containers = containers;
	}

	public List<Container> getContainers() {
		return containers;
	}
	
	@Override
	public String toString(){
		return getNumber();
	}
	
}
