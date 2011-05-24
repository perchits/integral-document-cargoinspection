package com.docum.dao;

import java.util.List;

import com.docum.domain.po.common.Container;

public interface ContainerDao extends BaseDao {
	
	public static final String GET_CONTAINERS_BY_VOYAGE_QUERY = "getContainersByVoyage";

	public List<Container> getContainersByVoyage(Long voyageId);

}
