package com.docum.ui.converter;

import javax.faces.convert.FacesConverter;

import com.docum.domain.po.common.PurchaseOrder;

@FacesConverter(value = "purchaseOrderConverter")
public class PurchaseOrderConverter extends IdentifiedEntityAbstractConverter<PurchaseOrder> {
	public PurchaseOrderConverter() {
		super(PurchaseOrder.class);
	}
}
