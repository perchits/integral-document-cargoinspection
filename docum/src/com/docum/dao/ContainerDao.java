package com.docum.dao;

import java.util.List;

import com.docum.domain.po.common.Container;

public interface ContainerDao extends BaseDao {
	
	public static final String GET_CONTAINERS_BY_VOYAGE_QUERY = "getContainersByVoyage";
	public static final String GET_CONTAINERS_BY_INVOICE_QUERY = "getContainersByInvoice";
	public static final String GET_CONTAINERS_BY_PURCHASE_ORDER_QUERY = "getContainersByPurchaseOrder";
	public static final String GET_CONTAINERS_BY_BILL_OF_LADING_QUERY = "getContainersByBillOfLading";
	public static final String GET_FULL_CONTAINER_QUERY = "getFullContainer";
	public static final String GET_CONTAINERS_WITHOUT_REPORT_QUERY = "getContainersWithoutReport";
	public static final String GET_CONTAINERS_WITHOUT_REPORT_BY_VOYAGE_QUERY = "getContainersWithoutReportByVoyage";
	public static final String GET_CONTAINERS_BY_REPORT_QUERY = "getContainersByReport";

	public List<Container> getContainersByVoyage(Long voyageId);
	public List<Container> getContainersByInvoice(Long invoiceId);
	public List<Container> getContainersByPurchaseOrder(Long orderId);
	public List<Container> getContainersByBillOfLading(Long billOfLadingId);
	public List<Container> getContainersWithoutReport();
	public List<Container> getContainersByReport(Long reportId);
	public List<Container> getContainersWithoutReportByVoyage(Long voyageId);
}
