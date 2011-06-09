package com.docum.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.docum.dao.PurchaseOrderDao;
import com.docum.domain.po.common.PurchaseOrder;

@Repository
public class PurchaseOrderDaoImpl extends BaseDaoImpl implements PurchaseOrderDao  {
	private static final long serialVersionUID = -629592691334300774L;

	@Override
	public List<PurchaseOrder> getOrdersByVoyage(Long voyageId) {
		//TODO Переписать на TypedQuery как только починят
		//     http://opensource.atlassian.com/projects/hibernate/browse/HHH-5348
		Query query =
			entityManager.createNamedQuery(GET_ORDERS_BY_VOYAGE_QUERY);
		query.setParameter("voyageId", voyageId);
		@SuppressWarnings("unchecked")
		List<PurchaseOrder> result = query.getResultList();
		return result;
	}

	@Override
	public List<PurchaseOrder> getOrdersByInvoice(Long invoiceId) {
		Query query = entityManager.createNamedQuery(GET_ORDERS_BY_INVOICE_QUERY);
		query.setParameter("invoiceId", invoiceId);
		@SuppressWarnings("unchecked")
		List<PurchaseOrder> result = query.getResultList();
		return result;
	}
	
}
