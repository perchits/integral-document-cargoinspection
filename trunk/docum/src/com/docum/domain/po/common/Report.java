package com.docum.domain.po.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.docum.dao.ReportingDao;
import com.docum.domain.po.IdentifiedEntity;

@Entity
@NamedQueries({
	@NamedQuery(
			name = ReportingDao.GET_REPORTS_BY_VOYAGE_QUERY,
			query = "select distinct rep from Report rep join rep.containers c " +
				"join c.voyage v " +
				"where v.id = :voyageId " +
				"order by rep.id"
	),
	@NamedQuery(
			name = ReportingDao.GET_REPORT_NUMBER_WITHIN_YEAR,
			query = "select count(*) from Report rep " +
				"where year(rep.date) = :year "
	)
})
public class Report extends IdentifiedEntity{
	private static final long serialVersionUID = 4735988174583929581L;

	private String number;
	private Date date;
	@ManyToOne
	private Customer customer;
	@ManyToMany
	private List<Container> containers = new ArrayList<Container>();

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public List<Container> getContainers() {
		return containers;
	}

	public void setContainers(List<Container> containers) {
		this.containers = containers;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
