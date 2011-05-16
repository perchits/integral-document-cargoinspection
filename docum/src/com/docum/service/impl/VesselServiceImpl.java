package com.docum.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docum.dao.VesselDao;
import com.docum.persistence.common.Vessel;
import com.docum.service.VesselService;

@Service("vesselService")
public class VesselServiceImpl implements VesselService, Serializable {
	private static final long serialVersionUID = 500180831144552695L;
	
	@Autowired
	private VesselDao vesselDao; 
	
	@Override
	public Long saveVessel(Vessel vessel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vessel getVessel(Long vesselId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vessel getVessel(String vesselName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Vessel> getAllVessels() {
		return vesselDao.getAll(Vessel.class, new String[]{"name"});
	}

	@Override
	public void deleteVessel(Vessel vessel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteVessel(Long vesselId) {
		// TODO Auto-generated method stub

	}

}
