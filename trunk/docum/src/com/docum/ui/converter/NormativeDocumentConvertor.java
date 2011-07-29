package com.docum.ui.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.web.jsf.FacesContextUtils;

import com.docum.domain.po.common.NormativeDocument;
import com.docum.service.BaseService;

@FacesConverter(value = "normativeDocumentConvertor")
public class NormativeDocumentConvertor implements Converter {
	private BaseService getBaseService(FacesContext ctx) {
		BaseService svc = (BaseService) FacesContextUtils
				.getWebApplicationContext(ctx).getBean("baseService");
		return svc;
	}
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}
		try {
			return getBaseService(context).getObject(NormativeDocument.class, Long.parseLong(value));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		NormativeDocument normativeDocument;
		if (value instanceof NormativeDocument) {
			normativeDocument = (NormativeDocument) value;
			if (normativeDocument.getId() == null)
				throw new IllegalArgumentException(
						"Cannot convert NormativeDocument object with null id.");
			return normativeDocument.getId().toString();
		} else {
			return "";
		}
	}
}
