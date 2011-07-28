package com.docum.ui.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.web.jsf.FacesContextUtils;

import com.docum.domain.po.common.Surveyor;
import com.docum.service.BaseService;

@FacesConverter(value = "surveyorConverter")
public class SurveyorConverter implements Converter {
	
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
			return getBaseService(context).getObject(Surveyor.class, Long.parseLong(value));
		} catch (NumberFormatException e) {
			return null;
		}		
	}
	
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		Surveyor s;
		if (value instanceof Surveyor) {
			s = (Surveyor) value;
			if (s.getId() == null)
				throw new IllegalArgumentException(
						"Cannot convert Surveyor object with null id.");
			return s.getId().toString();
		} else {
			return "";
		}
	}

}
