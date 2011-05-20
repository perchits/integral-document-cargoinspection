package com.docum.dao.impl;

import org.springframework.stereotype.Service;

import com.docum.dao.CargoDao;
import com.docum.domain.po.common.Cargo;

@Service("cargoDao")
public class CargoDaoImpl extends BaseDaoImpl implements CargoDao {
	private static final long serialVersionUID = -1849456554203104271L;

	@Override
	public Cargo getCargoByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cargo getCargoShortName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cargo getCargoByEnglishName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCargo(Long cargoId) {
		// TODO Auto-generated method stub

	}

}
