package com.docum.service;

import java.util.List;

import com.docum.domain.Stats;
import com.docum.domain.po.common.Cargo;

public interface StatsService {
	public static final String SERVICE_NAME = "statsService";
	public List<Stats.CargoParty> getCargoParties(Long invoiceId);
	public Stats.CargoDefectsOld extractCargoDefects(Cargo cargo);
	
	public List<Stats.CargoDefects> calcAverageDefects(Long containerId);
}
