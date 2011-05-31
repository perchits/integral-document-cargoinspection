package com.docum.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Invoice;
import com.docum.util.AlgoUtil;
import com.docum.view.dict.BaseView;
import com.docum.view.wrapper.ContainerPresentation;
import com.docum.view.wrapper.ContainerTransformer;

@Controller("containerBean")
@Scope("session")
public class ContainerView extends BaseView implements Serializable {

	private static final long serialVersionUID = 3476513399265370923L;
	private static final String sign = "Контейнер";
	private Container container = new Container();

	private ArrayList<ContainerPresentation> containers;

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBase() {
		return container.getNumber();
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return container != null ? container : new Invoice();
	}

	@Override
	public void refreshObjects() {
		super.refreshObjects();
		// TODO Посмотреть преобразование типов
		@SuppressWarnings("unchecked")
		Collection<Container> c = (Collection<Container>) getAllObjects();
		containers = new ArrayList<ContainerPresentation>(c.size());
		AlgoUtil.transform(containers, c, new ContainerTransformer());
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public Container getContainer() {
		return container;
	}

	public ArrayList<ContainerPresentation> getContainers() {
		if (containers == null) {
			refreshObjects();
		}
		return containers;
	}

}
