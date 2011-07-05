package com.docum.domain.po.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.docum.dao.VoyageDao;
import com.docum.domain.po.IdentifiedEntity;

@Entity
@NamedQueries({
		@NamedQuery(
				name = VoyageDao.GET_VOYAGES_BY_INVOICE,
				query = "SELECT DISTINCT v FROM Voyage v JOIN v.containers c " +
					"JOIN c.invoices i " + 
					"WHERE i.id=:invoiceId"
		),
		@NamedQuery(
				name = VoyageDao.GET_VOYAGES_BY_PURCHASE_ORDER,
				query = "SELECT DISTINCT v FROM Voyage v JOIN v.containers c " +
					"JOIN c.orders o " + 
					"WHERE o.id=:orderId"
		),
		@NamedQuery(
				name = VoyageDao.GET_VOYAGES_BY_BILL_OF_LADING,
				query = "SELECT DISTINCT v FROM Voyage v JOIN v.containers c " +
					"JOIN c.billOfLadings b " + 
					"WHERE b.id=:billOfLadingId"
		)
})
public class Voyage extends IdentifiedEntity{
	private static final long serialVersionUID = 8878667599057177012L;
	
	@ManyToOne(optional=false)
	private Vessel vessel;
	
	private String number;
	
	private Date arrivalDate;
	
	private boolean finished = false;
	
	@OneToMany(mappedBy = "voyage")
	private List<Container> containers = new ArrayList<Container>();
	
	public Voyage(){
		super();
	}

	public Voyage(Vessel vessel, String number, Date arrivalDate, boolean finished) {
		super();
		this.vessel = vessel;
		this.number = number;
		this.arrivalDate = arrivalDate;
		this.finished = finished;
	}

	public Vessel getVessel() {
		return vessel;
	}

	public void setVessel(Vessel vessel) {
		this.vessel = vessel;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date date) {
		this.arrivalDate = date;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public boolean isFinished() {
		return finished;
	}

	public List<Container> getContainers() {
		return containers;
	}

	public void setContainers(List<Container> containers) {
		this.containers = containers;
	}
}
