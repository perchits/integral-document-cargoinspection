package com.docum.ui.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.web.jsf.FacesContextUtils;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.service.BaseService;

public abstract class IdentifiedEntityAbstractConverter <T extends IdentifiedEntity>
		implements Converter {
	
	private Class<T> clazz;
	
	protected IdentifiedEntityAbstractConverter(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	private BaseService getBaseService(FacesContext ctx) {
		BaseService svc = (BaseService) FacesContextUtils.getWebApplicationContext(ctx).getBean(
				"baseService");
		return svc;
	}
	
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}
		try {
			return getBaseService(context).getObject(clazz, Long.parseLong(value));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else if (value.getClass().equals(clazz)) {
			@SuppressWarnings("unchecked")
			T entity = (T) value;
			if (entity.getId() == null)
				throw new IllegalArgumentException(
						"Cannot convert " + clazz.getName() + " object with null id.");
			return entity.getId().toString();
		} else {
			throw new IllegalArgumentException("Wrong class - " + value.getClass().getSimpleName() + 
				", expected - " + clazz.getSimpleName());
		}
	}
}
