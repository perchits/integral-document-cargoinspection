package com.docum.ui.converter;

import javax.faces.convert.FacesConverter;

import com.docum.domain.po.common.Invoice;

@FacesConverter(value = "invoiceConverter")
public class InvoiceConverter extends IdentifiedEntityAbstractConverter<Invoice> {
	public InvoiceConverter() {
		super(Invoice.class);
	}
}
