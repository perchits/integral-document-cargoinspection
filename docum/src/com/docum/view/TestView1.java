package com.docum.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.common.City;

@Controller("testView1")
@Scope("request")
public class TestView1 implements Serializable{
	private static final long serialVersionUID = -4244436687047228140L;
	private City city;
	
	@PostConstruct
	public void create() {
		city = (City)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("city");
	}
	public String getValue() {
		return city == null ? "???" : city.getName();
	}
}
