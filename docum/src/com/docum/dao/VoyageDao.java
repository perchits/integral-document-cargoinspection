package com.docum.dao;

import java.util.List;

import com.docum.domain.po.common.Voyage;

public interface VoyageDao extends BaseDao {
	public static  final String GET_VOYAGES_BY_INVOICE = "getVoyagesByInvoice";
	public static  final String GET_VOYAGES_BY_PURCHASE_ORDER = "getVoyagesByPurchaseOrder";
	
	public List<Voyage> getVoyagesByFinishStatus(boolean finished);
	public List<Voyage> getVoyagesByInvoice(Long invoiceId);
	public List<Voyage> getVoyagesByPurchaseOrder(Long orderId);
}
