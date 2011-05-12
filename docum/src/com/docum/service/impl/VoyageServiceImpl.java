package com.docum.service.impl;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docum.dao.VoyageDao;
import com.docum.persistence.common.Voyage;
import com.docum.service.VoyageService;

@Service("voyageService")
public class VoyageServiceImpl implements VoyageService, Serializable {
	private static final long serialVersionUID = -5052727843476156745L;
	
	@Autowired
	private VoyageDao voyageDao;
	
	@Override
	public Collection<Voyage> getAllVoyages() {
		return voyageDao.getAll(Voyage.class);
	}

	@Override
	public Collection<Voyage> getFinishedVoyages() {
		return voyageDao.getVoyagesByFinishStatus(true);
	}

	@Override
	public Collection<Voyage> getUnfinishedVoyages() {
		return voyageDao.getVoyagesByFinishStatus(false);
	}

}
