package com.docum.dao;

import java.util.List;

import com.docum.domain.po.common.BillOfLading;

public interface BillOfLadingDao extends BaseDao {

	public static final String GET_BILLS_BY_VOYAGE_QUERY = "getBillsByVoyage";
	public static final String GET_BILLS_BY_INVOICE_QUERY = "getBillsByInvoice";
	public static final String GET_BILLS_BY_PURCHASE_ORDER_QUERY = "getBillsByPurchaseOrder";

	public List<BillOfLading> getBillsByVoyage(Long voyageId);
	public List<BillOfLading> getBillsByInvoice(Long invoiceId);
	public List<BillOfLading> getBillsByPurchaseOrder(Long orderId);
}