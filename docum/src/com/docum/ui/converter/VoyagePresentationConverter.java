package com.docum.ui.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.web.jsf.FacesContextUtils;

import com.docum.domain.po.common.Voyage;
import com.docum.service.BaseService;
import com.docum.view.wrapper.VoyagePresentation;

@FacesConverter(value = "voyagePresentationConverter")
public class VoyagePresentationConverter implements Converter {
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
			return new VoyagePresentation(
					getBaseService(context).getObject(Voyage.class, Long.parseLong(value)), false);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		VoyagePresentation voyage;
		if (value instanceof VoyagePresentation) {
			voyage = (VoyagePresentation) value;
			if (voyage.getVoyage().getId() == null)
				throw new IllegalArgumentException(
						"Cannot convert VoyagePresentation object with null id.");
			return voyage.getVoyage().getId().toString();
		} else {
			return "";
		}
	}
}
