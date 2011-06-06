package com.docum.view;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.common.City;
import com.docum.service.BaseService;

@Controller("testView")
@Scope("view")
public class TestView implements Serializable{
	private static final long serialVersionUID = 5827007837538386824L;
	@Autowired
	BaseService baseService;
	
	public Object save() {
		City city = new City("Питер", false);
		baseService.saveObject(city);
		return "test";
	}

	public Object go() {
		City city = baseService.getObject(City.class, 1L);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("city", city);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("city1", getCity());
		return "test1?faces-redirect=true";
	}
	
	public City getCity() {
		return baseService.getObject(City.class, 2L);
	}
}
