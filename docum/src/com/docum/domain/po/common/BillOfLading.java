package com.docum.domain.po.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.docum.dao.BillOfLadingDao;
import com.docum.domain.po.IdentifiedEntity;

@Entity
@NamedQueries(
		@NamedQuery(
				name = BillOfLadingDao.GET_BILLS_BY_VOYAGE_QUERY,
				query = "SELECT DISTINCT bill FROM BillOfLading bill JOIN bill.containers c " +
					"WHERE c.voyage.id=:voyageId"
		)
)
public class BillOfLading extends IdentifiedEntity{
	private static final long serialVersionUID = 5640872642276855894L;

	private String number;

	@ManyToOne(optional=false)
	private Voyage voyage;
	
	@ManyToMany
	private List<Container> containers = new ArrayList<Container>();

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
