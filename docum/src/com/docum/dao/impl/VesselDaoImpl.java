package com.docum.dao.impl;

import org.springframework.stereotype.Service;

import com.docum.dao.VesselDao;
import com.docum.domain.po.common.Vessel;

@Service("vesselDao")
public class VesselDaoImpl extends BaseDaoImpl implements VesselDao {
	private static final long serialVersionUID = 409061579663672596L;

	@Override
	public Vessel getVessel(String vesselName) {
		// TODO Auto-generated method stub
		return null;
	}

}
