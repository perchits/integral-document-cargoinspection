package com.docum.service;

import java.util.Collection;

import com.docum.persistence.common.Voyage;

public interface VoyageService {
	public Collection<Voyage> getAllVoyages();
	public Collection<Voyage> getFinishedVoyages();
	public Collection<Voyage> getUnfinishedVoyages();

}
