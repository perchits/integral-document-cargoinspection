package com.docum.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docum.dao.CityDao;
import com.docum.persistence.common.City;
import com.docum.service.CityService;

@Service("cityService")
@Transactional
public class CityServiceImpl implements CityService, Serializable {
	private static final long serialVersionUID = 4716974351246051114L;

	@Autowired
	private CityDao cityDao; 
	
	@Override
	public City saveCity(City city) {
		return cityDao.saveObject(city);
	}

	@Override
	public City getCity(Long cityId) {
		return cityDao.getObject(City.class, cityId);
	}

	@Override
	public List<City> getAllCities() {
		return cityDao.getAll(City.class, null);
	}

	@Override
	public void deleteCity(City city) {
		cityDao.deleteObject(city);

	}

}
