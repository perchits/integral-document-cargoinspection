package com.docum.service;

import java.util.Collection;
import java.util.List;

import com.docum.domain.po.common.Voyage;

public interface VoyageService {
	public List<Voyage> getAllVoyages();
	public Collection<Voyage> getFinishedVoyages();
	public Collection<Voyage> getUnfinishedVoyages();
	public Voyage getVoyage(Long voyageId);
	public Voyage saveVoyage(Voyage voyage);
	public void deleteVoyage(Voyage voyage);
	public void deleteVoyage(Long voyageId);
}