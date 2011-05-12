package com.docum.service;

import java.util.List;

import com.docum.persistence.common.Vessel;

public interface VesselService {
	
	public Long saveVessel(Vessel vessel);
	
	public Vessel getVessel(Long vesselId);
	
	public Vessel getVessel(String vesselName);
	
	public List<Vessel> getAllVessels();
	
	public void deleteVessel(Vessel vessel);
	
	public void deleteVessel(Long vesselId);
}
