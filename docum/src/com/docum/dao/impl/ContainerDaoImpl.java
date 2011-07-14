package com.docum.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.docum.dao.ContainerDao;
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.CargoPackage;
import com.docum.domain.po.common.Container;

@Repository
public class ContainerDaoImpl extends BaseDaoImpl implements ContainerDao {

	private static final long serialVersionUID = 6738371757481216033L;

	@Override
	public List<Container> getContainersByVoyage(Long voyageId) {
		Query query = entityManager
				.createNamedQuery(GET_CONTAINERS_BY_VOYAGE_QUERY);
		query.setParameter("voyageId", voyageId);
		@SuppressWarnings("unchecked")
		List<Container> result = query.getResultList();
		return result;
	}

	@Override
	public List<Container> getContainersByInvoice(Long invoiceId) {
		Query query = entityManager.createNamedQuery(GET_CONTAINERS_BY_INVOICE_QUERY);
		query.setParameter("invoiceId", invoiceId);
		@SuppressWarnings("unchecked")
		List<Container> result = query.getResultList();
		return result;
	}

	@Override
	public List<Container> getContainersByPurchaseOrder(Long orderId) {
		Query query = entityManager.createNamedQuery(GET_CONTAINERS_BY_PURCHASE_ORDER_QUERY);
		query.setParameter("orderId", orderId);
		@SuppressWarnings("unchecked")
		List<Container> result = query.getResultList();
		return result;
	}
	
	@Override
	public List<Container> getContainersByBillOfLading(Long billOfLadingId) {
		Query query = entityManager.createNamedQuery(GET_CONTAINERS_BY_BILL_OF_LADING_QUERY);
		query.setParameter("billOfLadingId", billOfLadingId);
		@SuppressWarnings("unchecked")
		List<Container> result = query.getResultList();
		return result;
	}

	@Override
	public Container getContainer(Long containerId) {
//		TypedQuery<Container> query =
//			entityManager.createNamedQuery(GET_FULL_CONTAINER_QUERY, Container.class);
//		query.setParameter("containerId", containerId);
//		Container result = query.getSingleResult();
//		return result;
//TODO: решить вопрос с GET_FULL_CONTAINER_QUERY
		Container result = getObject(Container.class, containerId);
		result.getInvoices().size();
		result.getBillOfLadings().size();
		result.getOrders().size();
		for(Cargo c : result.getCargoes()){
			for(CargoPackage cp : c.getActualCondition().getCargoPackages()){
				cp.getCalibres().size();
			}
			for(CargoPackage cp : c.getDeclaredCondition().getCargoPackages()){
				cp.getCalibres().size();
			}
			c.getFeatures().size();
		}
		return result;
	}
	
	@Override
	public List<Container> getContainersWithoutReport() {
		Query query = entityManager.createNamedQuery(GET_CONTAINERS_WITHOUT_REPORT_QUERY);
		@SuppressWarnings("unchecked")
		List<Container> result = query.getResultList();
		return result;
	}
	
	@Override
	public List<Container> getContainersByReport(Long reportId) {
		Query query = entityManager.createNamedQuery(GET_CONTAINERS_BY_REPORT_QUERY);
		query.setParameter("reportId", reportId);
		@SuppressWarnings("unchecked")
		List<Container> result = query.getResultList();
		return result;
	}
}
