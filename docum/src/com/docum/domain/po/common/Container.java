package com.docum.domain.po.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.docum.dao.ContainerDao;
import com.docum.domain.ContainerStateEnum;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
@NamedQueries({
		@NamedQuery(
				name = ContainerDao.GET_CONTAINERS_BY_VOYAGE_QUERY,
				query = "SELECT DISTINCT c FROM Container c " +
					"WHERE c.voyage.id=:voyageId"
		), 
		@NamedQuery(
				name = ContainerDao.GET_CONTAINERS_BY_INVOICE_QUERY,
				query = "SELECT DISTINCT c FROM Container c " +
					"JOIN c.invoices i " +
					"WHERE i.id=:invoiceId"
		),
		@NamedQuery(
				name = ContainerDao.GET_CONTAINERS_BY_PURCHASE_ORDER_QUERY,
				query = "SELECT DISTINCT c FROM Container c " +
					"JOIN c.orders o " +
					"WHERE o.id=:orderId"
		),
		@NamedQuery(
				name = ContainerDao.GET_CONTAINERS_BY_BILL_OF_LADING_QUERY,
				query = "SELECT DISTINCT c FROM Container c " +
					"JOIN c.billOfLadings b " +
					"WHERE b.id=:billOfLadingId"
		),
		@NamedQuery(
				name = ContainerDao.GET_FULL_CONTAINER_QUERY,
				query = "SELECT DISTINCT c FROM Container c FETCH ALL PROPERTIES " +
//TODO: С этим запросом вылетает ошибка "cannot simultaneously fetch multiple bags"
// Варианты решения тут: http://community.jboss.org/message/361474#4045327
//					"LEFT JOIN FETCH c.invoices " +
//					"LEFT JOIN FETCH c.orders " +
//					"LEFT JOIN FETCH c.billOfLadings " +
//					"LEFT JOIN FETCH c.cargoes crg " +
//					"LEFT JOIN FETCH crg.features " +
					"WHERE c.id=:containerId"
		)
})
public class Container extends IdentifiedEntity {
	private static final long serialVersionUID = -1325845205678208996L;

	private String number;
	
	@Enumerated
	private ContainerStateEnum state = ContainerStateEnum.BEFORE_CUSTOMS;

	@ManyToMany(mappedBy = "containers")
	private List<Invoice> invoices = new ArrayList<Invoice>();
	
	@ManyToMany(mappedBy = "containers")
	private List<PurchaseOrder> orders = new ArrayList<PurchaseOrder>();
	
	@ManyToMany(mappedBy = "containers")
	private List<BillOfLading> billOfLadings = new ArrayList<BillOfLading>();

	@OneToMany(mappedBy = "container")
	private List<Cargo> cargoes = new ArrayList<Cargo>();
	
	@ManyToOne
	private Voyage voyage;
	
	@ManyToOne
	private City city;

	@ManyToOne
	private Port loadingPort;
	
	private Date loadingDate;

	@ManyToOne
	private Port dischargePort;

	private Date dischargeDate;
	
	private String declaredSeal;

	private String actualSeal;
	
	public Container() {
		super();
	}

	public Container(String number) {
		super();
		this.number = number;
	}

	public Container(String number, ContainerStateEnum state, Voyage voyage, City city) {
		super();
		this.number = number;
		this.state = state;
		this.voyage = voyage;
		this.city = city;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String name) {
		this.number = name;
	}

	public ContainerStateEnum getState() {
		return state;
	}

	public void setState(ContainerStateEnum state) {
		this.state = state;
	}
	
	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public List<PurchaseOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<PurchaseOrder> orders) {
		this.orders = orders;
	}

	public List<BillOfLading> getBillOfLadings() {
		return billOfLadings;
	}

	public void setBillOfLadings(List<BillOfLading> billOfLadings) {
		this.billOfLadings = billOfLadings;
	}

	public List<Cargo> getCargoes() {
		return cargoes;
	}

	public void setCargoes(List<Cargo> cargoes) {
		this.cargoes = cargoes;
	}

	public Voyage getVoyage() {
		return voyage;
	}

	public void setVoyage(Voyage voyage) {
		this.voyage = voyage;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Port getLoadingPort() {
		return loadingPort;
	}

	public void setLoadingPort(Port loadingPort) {
		this.loadingPort = loadingPort;
	}

	public Date getLoadingDate() {
		return loadingDate;
	}

	public void setLoadingDate(Date loadingDate) {
		this.loadingDate = loadingDate;
	}

	public Port getDischargePort() {
		return dischargePort;
	}

	public void setDischargePort(Port dischargePort) {
		this.dischargePort = dischargePort;
	}

	public Date getDischargeDate() {
		return dischargeDate;
	}

	public void setDischargeDate(Date dischargeDate) {
		this.dischargeDate = dischargeDate;
	}

	public String getDeclaredSeal() {
		return declaredSeal;
	}

	public void setDeclaredSeal(String declaredSeal) {
		this.declaredSeal = declaredSeal;
	}

	public String getActualSeal() {
		return actualSeal;
	}

	public void setActualSeal(String actualSeal) {
		this.actualSeal = actualSeal;
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Container)) {
			return false;
		}

		return EqualsHelper.equals(getId(), ((Container) obj).getId());
	}

	public int hashCode() {
		return HashCodeHelper.hashCode(getId());
	}
}
