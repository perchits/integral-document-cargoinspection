package com.docum.service;

import java.util.List;

import com.docum.domain.po.common.PurchaseOrder;

public interface PurchaseOrderService extends BaseService {
	public static final String SERVICE_NAME = "purchaseOrderService";
	
	public List<PurchaseOrder> getOrdersByVoyage(Long voyageId);
	public List<PurchaseOrder> getOrdersByInvoice(Long invoiceId);
}
