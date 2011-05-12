package com.docum.dao;

import java.util.List;

import com.docum.persistence.common.Voyage;

public interface VoyageDao extends BaseDao {
	public List<Voyage> getVoyagesByFinishStatus(boolean finished);
}
