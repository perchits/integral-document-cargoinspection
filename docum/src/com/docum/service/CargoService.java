package com.docum.service;

import com.docum.domain.po.common.CargoInspectionInfo;

public interface CargoService extends BaseService {

	public static final String SERVICE_NAME = "cargoService";

	public CargoInspectionInfo getCargoInspectionInfo(Long cargoId);
}
