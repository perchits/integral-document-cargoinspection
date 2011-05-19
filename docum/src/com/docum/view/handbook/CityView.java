package com.docum.view.handbook;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.docum.persistence.IdentifiedEntity;
import com.docum.persistence.common.City;

@ManagedBean(name = "cityBean")
@SessionScoped
public class CityView extends BaseView implements Serializable {
	private static final long serialVersionUID = -6124629281863739318L;
	private static final String sign = "Город";
		
	private City city = new City();

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@Override
	public void newObject() {
		super.newObject();
		this.city = new City();		
	}
	
	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBase() {
		return city.getName();
	}

	@Override
	public IdentifiedEntity getBeanObject() {		
		return city != null ? this.city : new City();
	}

}
