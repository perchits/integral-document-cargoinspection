package com.docum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docum.dao.CargoDao;
import com.docum.domain.po.common.CargoInspectionInfo;
import com.docum.service.CargoService;

@Service(CargoService.SERVICE_NAME)
@Transactional
public class CargoServiceImpl extends BaseServiceImpl implements CargoService {
	private static final long serialVersionUID = 1716750300940533007L;

	@Autowired
	public CargoDao cargoDao;

	@Override
	public CargoInspectionInfo getCargoInspectionInfo(Long cargoId) {
		return cargoDao.getCargoInspectionInfo(cargoId);
	}
}
