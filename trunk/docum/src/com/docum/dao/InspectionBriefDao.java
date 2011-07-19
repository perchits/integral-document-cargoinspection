package com.docum.dao;

import com.docum.domain.po.common.InspectionBrief;

public interface InspectionBriefDao extends BaseDao{
	public static final String GET_INSPECTION_BRIEF_BY_CONTAINER_QUERY = "getInspectionBriefByContainer";
	
	public InspectionBrief getInspectionBriefByContainer(Long container);
}
