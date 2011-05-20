package com.docum.view.handbook;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Company;

@ManagedBean(name = "companyBean")
@SessionScoped
public class CompanyView extends BaseView implements Serializable {

	private static final long serialVersionUID = -1126312045148623962L;
	private static final String sign = "Контрагент";
	private Company company = new Company();

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBase() {
		return company.getName();
	}

	@Override
	public void newObject() {
		super.newObject();
		this.company = new Company();		
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return company != null ? this.company : new Company();		
	}

	public void setCompany(Company mesure) {		
			this.company = mesure;		
	}

	public Company getCompany() {
		return company;
	}

}
