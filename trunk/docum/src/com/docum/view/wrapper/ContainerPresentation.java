package com.docum.view.wrapper;

import java.io.Serializable;
import java.util.EnumMap;

import com.docum.domain.ContainerStateEnum;
import com.docum.domain.po.common.Container;

public class ContainerPresentation implements Serializable {
	private static final long serialVersionUID = 3960028459943599183L;
	private Container container;
	private static EnumMap<ContainerStateEnum, String> containerStateMap = new EnumMap<ContainerStateEnum, String>(
			ContainerStateEnum.class);

	static {
		containerStateMap.put(ContainerStateEnum.BEFORE_CUSTOMS,
				"before-customs-color");
		containerStateMap.put(ContainerStateEnum.AFTER_CUSTOMS,
				"after-customs-color");
		containerStateMap.put(ContainerStateEnum.HANDLED, "handled-color");
		containerStateMap.put(ContainerStateEnum.REPORTED, "reported-color");
		containerStateMap.put(ContainerStateEnum.ABANDONED, "abandoned-color");
	}

	public ContainerPresentation(Container container) {
		this.container = container;
	}

	public String getNumber() {
		return container.getNumber();
	}

	public String getCity() {
		return container.getCity().getName();
	}

	public String getState() {
		return container.getState().getName();
	}

	public String getColor() {
		return containerStateMap.get(container.getState());
	}
}
