package com.docum.service;

import java.util.List;

import com.docum.domain.Stats;

public interface StatsService {
	public static final String SERVICE_NAME = "statsService";
	public List<Stats.CargoParty> getCargoParties(Long invoiceId);
}
