package com.docum.util.stats;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.Container;

public class StatsHelper {

	private static class CargoGroupListMaker {
		Map<StatsCargoGroupInfo, StatsCargoGroup> groups =
			new HashMap<StatsCargoGroupInfo, StatsCargoGroup>();
		
		public void add(Cargo cargo) {
			StatsCargoGroupInfo info = new StatsCargoGroupInfo(cargo);
			StatsCargoGroup group = groups.get(info);
			if(group != null) {
				group.addCargo(cargo);
			} else {
				groups.put(info, new StatsCargoGroup(info));
			}
		}
		
		public Collection<StatsCargoGroup> getGroups() {
			return groups.values();
		}
	}
	
	public static Collection<StatsCargoGroup> getCargoGroups(Collection<Container> containers) {
		CargoGroupListMaker maker = new CargoGroupListMaker();
		for(Container container : containers) {
			Set<Cargo> cargoes = container.getActualCondition().getCargoes();
			for(Cargo cargo : cargoes) {
				maker.add(cargo);
			}
		}
		return maker.getGroups();
	}
}
