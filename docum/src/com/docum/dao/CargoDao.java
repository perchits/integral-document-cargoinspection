package com.docum.dao;

import com.docum.domain.po.common.Cargo;

public interface CargoDao extends BaseDao{
	
	public Cargo getCargoByName(String name);
	
	public Cargo getCargoShortName(String name);
	
	public Cargo getCargoByEnglishName(String name);
	
	public void deleteCargo(Long cargoId);

}
