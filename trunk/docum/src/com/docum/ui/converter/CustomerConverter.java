package com.docum.ui.converter;

import javax.faces.convert.FacesConverter;

import com.docum.domain.po.common.Customer;

@FacesConverter(value = "customerConverter")
public class CustomerConverter extends IdentifiedEntityAbstractConverter<Customer> {
	public CustomerConverter() {
		super(Customer.class);
	}
	
}
