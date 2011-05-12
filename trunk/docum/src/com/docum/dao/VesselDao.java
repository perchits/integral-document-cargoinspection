package com.docum.dao;

import com.docum.persistence.common.Vessel;

public interface VesselDao extends BaseDao {
	
	public Vessel getVessel(String vesselName);	
}
