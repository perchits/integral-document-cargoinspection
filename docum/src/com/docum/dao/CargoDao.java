package com.docum.dao;

import com.docum.domain.po.common.CargoInspectionInfo;

public interface CargoDao extends BaseDao{
	public static final String GET_CARGO_INSPECTION_INFO = "getCargoInspectionInfo";
	
	public CargoInspectionInfo getCargoInspectionInfo(Long cargoId);
}
