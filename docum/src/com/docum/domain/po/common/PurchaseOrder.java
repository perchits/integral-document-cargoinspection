package com.docum.domain.po.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.docum.dao.PurchaseOrderDao;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
@NamedQueries(
		@NamedQuery(
				name = PurchaseOrderDao.GET_ORDERS_BY_VOYAGE_QUERY,
				query = "SELECT DISTINCT po FROM PurchaseOrder po JOIN po.containers c " +
					"WHERE c.voyage.id=:voyageId"
		)
)
public class PurchaseOrder extends IdentifiedEntity {
	private static final long serialVersionUID = -5483165526419958870L;
	
	private String number;

	@ManyToOne(optional=false)
	private Voyage voyage;

	@ManyToMany
	private List<Container> containers = new ArrayList<Container>();

	public PurchaseOrder() {
		super();
	}

	public PurchaseOrder(Voyage voyage, String number) {
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

	public void setContainers(List<Container> containers) {
		this.containers = containers;
	}

	public List<Container> getContainers() {
		return containers;
	}

	public Voyage getVoyage() {
		return voyage;
	}

	public void setVoyage(Voyage voyage) {
		this.voyage = voyage;
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof PurchaseOrder)) {
			return false;
		}
		return EqualsHelper.equals(getId(), ((PurchaseOrder) obj).getId());
	}

	public int hashCode() {
		return HashCodeHelper.hashCode(getId());
	}
	
	@Override
	public String toString(){
		return getNumber();
	}
}
