package com.docum.dao;

import java.util.List;

import com.docum.domain.po.common.Voyage;

public interface VoyageDao extends BaseDao {
	public List<Voyage> getVoyagesByFinishStatus(boolean finished);
}
