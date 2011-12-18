package com.docum.view.dict;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.SortOrderEnum;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Port;

@Controller("portBean")
@Scope("session")
public class PortView extends BaseView {

	private static final long serialVersionUID = 4140404602418969773L;
	private static final String sign = "Порт";
	
	private Port port = new Port();
	
	public Port getPort() {
		return port;
	}
	
	public void setPort(Port port) {
		this.port = port;
	}
	
	@Override
	public void newObject() {
		super.newObject();
		this.port = new Port();
	}

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBriefInfo() {
		return port.getName();
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return port != null ? this.port : new Port();
	}
	
	@Override
	public Map<String, SortOrderEnum> getDefaultSortFields(){
		HashMap<String, SortOrderEnum> sortFields = new HashMap<String, SortOrderEnum>();
		sortFields.put("name", SortOrderEnum.ASC);
		return sortFields;
	}
}