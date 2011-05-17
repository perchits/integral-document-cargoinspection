package com.docum.service;

import java.util.List;

import com.docum.persistence.common.City;

public interface CityService {
	
	public City saveCity(City city);
	
	public City getCity(Long cityId);	
		
	public List<City> getAllCities();
	
	public void deleteCity(City city);	
	
}
