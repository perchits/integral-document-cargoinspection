package com.docum.util.stats;

import java.util.ArrayList;
import java.util.List;

import com.docum.domain.po.common.Cargo;

/**
 * Группа грузов с одинаковыми уникальными характеристиками
 *
 */
public class StatsCargoGroup {
	private StatsCargoGroupInfo info;
	private List<Cargo> cargoes = new ArrayList<Cargo>();
	
	public StatsCargoGroup(StatsCargoGroupInfo info) {
		this.info = info;
	}

	public StatsCargoGroupInfo getInfo() {
		return info;
	}

	public List<Cargo> getCargoes() {
		return cargoes;
	}
	
	public void addCargo(Cargo cargo) {
		cargoes.add(cargo);
	}
}