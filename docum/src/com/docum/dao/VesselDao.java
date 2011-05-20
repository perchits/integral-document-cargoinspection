package com.docum.dao;

import com.docum.domain.po.common.Vessel;

public interface VesselDao extends BaseDao {
	
	public Vessel getVessel(String vesselName);	
}
