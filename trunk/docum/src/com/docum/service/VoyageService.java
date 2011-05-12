package com.docum.service;

import java.util.Collection;
import java.util.List;

import com.docum.persistence.common.Voyage;

public interface VoyageService {
	public List<Voyage> getAllVoyages();
	public Collection<Voyage> getFinishedVoyages();
	public Collection<Voyage> getUnfinishedVoyages();

}
