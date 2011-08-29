package com.docum.ui.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.web.jsf.FacesContextUtils;

import com.docum.domain.po.common.Container;
import com.docum.service.BaseService;
import com.docum.view.wrapper.ContainerPresentation;

@FacesConverter(value = "containerPresentationConverter")
public class ContainerPresentationConverter implements Converter {
	
	@SuppressWarnings("rawtypes")
	Class clazz = ContainerPresentation.class;
	
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
			return new ContainerPresentation(
				getBaseService(context).getObject(Container.class, Long.parseLong(value)));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else if (value.getClass().equals(clazz)) {
			ContainerPresentation entity = (ContainerPresentation) value;
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
