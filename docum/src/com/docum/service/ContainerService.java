package com.docum.service;

import java.util.List;

import com.docum.domain.po.common.Container;

public interface ContainerService extends BaseService {

	public static final String SERVICE_NAME = "containerService";

	public List<Container> getContainersByVoyage(Long voyageId);
	public List<Container> getContainersByInvoice(Long invoiceId);
	public List<Container> getContainersByPurchaseOrder(Long orderId);
	public List<Container> getContainersByBillOfLading(Long billOfLadingId);
	public List<Container> getContainersWithoutReport();
	public List<Container> getContainersByReport(Long reportId);
	public List<Container> getContainersWithoutReportByVoyage(Long voyageId);
	
	public Container getContainer(Long containerId);
	public Container saveContainer(Container container);
}
