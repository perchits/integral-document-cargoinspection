package com.docum.view.handbook;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.docum.persistence.IdentifiedEntity;
import com.docum.persistence.common.City;
import com.docum.service.CityService;

@ManagedBean(name = "cityBean")
@SessionScoped
public class CityView extends BaseView implements Serializable {
	private static final long serialVersionUID = -6124629281863739318L;
	private static final String sign = "Город";
	@ManagedProperty(value = "#{cityService}")
	private CityService cityService;
	private List<City> cities;
	private City city = new City();

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public List<City> getCities() {
		if (cities == null) {
			refreshCities();
		}
		return cities;
	}

	public void refreshCities() {
		cities = cityService.getAllCities();
	}

	public void deleteCity() {
		cityService.deleteCity(cityService.getCity(city.getId()));
		refreshCities();
	}

	public void newCity() {
		city = new City();
		setTitle("Новый " + getSign());
	}

	public void saveCityAction() {
		if (this.city.getId() != null) {
			City city = cityService.getCity(this.city
					.getId());
			city.copy(this.city);
			this.city = city;
		}
		this.city = cityService.saveCity(city);
		refreshCities();
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
		return city;
	}

	@Override
	public void refreshObjects() {
		// TODO Auto-generated method stub
		
	}

}
