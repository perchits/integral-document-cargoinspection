package com.docum.dao;

import java.util.List;

import com.docum.domain.po.common.PurchaseOrder;

public interface PurchaseOrderDao extends BaseDao {
	
	public static final String GET_ORDERS_BY_VOYAGE_QUERY="getOrdersByVoyage";
	public static final String GET_ORDERS_BY_INVOICE_QUERY="getOrdersByInvoice";
	
	public List<PurchaseOrder> getOrdersByVoyage(Long voyageId);
	public List<PurchaseOrder> getOrdersByInvoice(Long invoiceId);

}
