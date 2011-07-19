package com.docum.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docum.dao.InspectionBriefDao;
import com.docum.domain.po.common.InspectionBrief;
import com.docum.service.InspectionBriefService;

@Service(InspectionBriefService.SERVICE_NAME)
@Transactional
public class InspectionBriefServiceImpl extends BaseServiceImpl implements InspectionBriefService, Serializable {

	private static final long serialVersionUID = -1391305343359265961L;
	
	@Autowired
	InspectionBriefDao inspectionBriefDao;

	@Override
	public InspectionBrief getInspectionBriefByContainer(Long containerId) {
		return inspectionBriefDao.getInspectionBriefByContainer(containerId);
	}

}
