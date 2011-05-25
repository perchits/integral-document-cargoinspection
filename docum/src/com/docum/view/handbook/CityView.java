package com.docum.view.handbook;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.City;

@Controller("cityBean")
@Scope("session")
public class CityView extends BaseView {
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
