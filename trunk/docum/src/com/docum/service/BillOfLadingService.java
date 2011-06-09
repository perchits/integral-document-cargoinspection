package com.docum.service;

import java.util.List;

import com.docum.domain.po.common.BillOfLading;

public interface BillOfLadingService extends BaseService {

	public static final String SERVICE_NAME = "billOfLadingService";

	public List<BillOfLading> getBillsByVoyage(Long voyageId);
	public List<BillOfLading> getBillsByInvoice(Long invoiceId);
	public List<BillOfLading> getBillsByPurchaseOrder(Long orderId);
}
