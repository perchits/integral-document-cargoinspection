package com.docum.domain.po.common;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.docum.dao.BillOfLadingDao;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
@NamedQueries({
		@NamedQuery(
				name = BillOfLadingDao.GET_BILLS_BY_VOYAGE_QUERY,
				query = "SELECT DISTINCT bill FROM BillOfLading bill " +
					"WHERE bill.voyage.id=:voyageId " +
					"ORDER BY bill.id"
		),
		@NamedQuery(
				name = BillOfLadingDao.GET_BILLS_BY_INVOICE_QUERY,
				query = "SELECT DISTINCT bill FROM BillOfLading bill JOIN bill.containers c " +
					"JOIN c.invoices i " + 
					"WHERE i.id=:invoiceId"
		),
		@NamedQuery(
				name = BillOfLadingDao.GET_BILLS_BY_PURCHASE_ORDER_QUERY,
				query = "SELECT DISTINCT bill FROM BillOfLading bill JOIN bill.containers c " +
					"JOIN c.orders o " + 
					"WHERE o.id=:orderId"
		)
})
public class BillOfLading extends IdentifiedEntity{
	private static final long serialVersionUID = 5640872642276855894L;

	private String number;

	@ManyToOne(optional=false)
	private Voyage voyage;
	
	@ManyToMany(mappedBy = "billOfLadings")
	private Set<Container> containers = new HashSet<Container>();

	public BillOfLading() {
		super();
	}

	public BillOfLading(Voyage voyage, String number) {
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

	public void setContainers(Set<Container> containers) {
		this.containers = containers;
	}

	public Set<Container> getContainers() {
		return containers;
	}
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof BillOfLading)) {
			return false;
		}
		return EqualsHelper.equals(getId(), ((BillOfLading) obj).getId());
	}

	public int hashCode() {
		return HashCodeHelper.hashCode(getId());
	}
	
	@Override
	public String toString(){
		return getNumber();
	}

}
