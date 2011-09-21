package com.docum.dao.impl;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.docum.dao.CargoDao;
import com.docum.domain.po.common.CargoInspectionInfo;

@Repository
public class CargoDaoImpl extends BaseDaoImpl implements CargoDao {
	private static final long serialVersionUID = -1849456554203104271L;
	private static final Logger logger = Logger.getLogger(CargoDaoImpl.class); 

	@Override
	public CargoInspectionInfo getCargoInspectionInfo(Long cargoId) {
		CargoInspectionInfo result = null;
		try {
			TypedQuery<CargoInspectionInfo> query =
				entityManager.createNamedQuery(GET_CARGO_INSPECTION_INFO, CargoInspectionInfo.class);
			query.setParameter("cargoId", cargoId);
			result = query.getSingleResult();
		} catch(NoResultException e) {
			logger.error("Can't fetch CargoInspectionInfo for cargo.id=" + cargoId);
		}
		return result;
	}
}
