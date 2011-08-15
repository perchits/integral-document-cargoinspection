package com.docum.ui.converter;

import javax.faces.convert.FacesConverter;

import com.docum.domain.po.common.BillOfLading;

@FacesConverter(value = "billOfLadingConverter")
public class BillOfLadingConverter extends IdentifiedEntityAbstractConverter<BillOfLading> {
	public BillOfLadingConverter() {
		super(BillOfLading.class);
	}
}
