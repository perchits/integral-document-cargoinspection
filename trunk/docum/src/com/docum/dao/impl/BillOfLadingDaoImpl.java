package com.docum.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.docum.dao.BillOfLadingDao;
import com.docum.domain.po.common.BillOfLading;

@Repository
public class BillOfLadingDaoImpl extends BaseDaoImpl implements BillOfLadingDao {

	private static final long serialVersionUID = -7680363829989502838L;

	@Override
	public List<BillOfLading> getBillsByVoyage(Long voyageId) {
		Query query = entityManager.createNamedQuery(GET_BILLS_BY_VOYAGE_QUERY);
		query.setParameter("voyageId", voyageId);
		@SuppressWarnings("unchecked")
		List<BillOfLading> result = query.getResultList();
		return result;
	}

	@Override
	public List<BillOfLading> getBillsByInvoice(Long invoiceId) {
		Query query = entityManager.createNamedQuery(GET_BILLS_BY_INVOICE_QUERY);
		query.setParameter("invoiceId", invoiceId);
		@SuppressWarnings("unchecked")
		List<BillOfLading> result = query.getResultList();
		return result;
	}


	@Override
	public List<BillOfLading> getBillsByPurchaseOrder(Long orderId) {
		Query query = entityManager.createNamedQuery(GET_BILLS_BY_PURCHASE_ORDER_QUERY);
		query.setParameter("orderId", orderId);
		@SuppressWarnings("unchecked")
		List<BillOfLading> result = query.getResultList();
		return result;
	}

}
