package com.docum.test.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.docum.domain.ContainerStateEnum;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.SecurityRoleEnum;
import com.docum.domain.po.common.ActualCargoCondition;
import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.BillOfLading;
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.City;
import com.docum.domain.po.common.Company;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Customer;
import com.docum.domain.po.common.DeclaredCargoCondition;
import com.docum.domain.po.common.Inspection;
import com.docum.domain.po.common.Invoice;
import com.docum.domain.po.common.Measure;
import com.docum.domain.po.common.Port;
import com.docum.domain.po.common.PurchaseOrder;
import com.docum.domain.po.common.SecurityRole;
import com.docum.domain.po.common.SecurityUser;
import com.docum.domain.po.common.Supplier;
import com.docum.domain.po.common.SurveyPlace;
import com.docum.domain.po.common.Surveyor;
import com.docum.domain.po.common.Vessel;
import com.docum.domain.po.common.Voyage;
import com.docum.service.CryptoService;
import com.docum.util.AlgoUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/docum-context.xml")
@TransactionConfiguration(defaultRollback=false)
@Transactional
public class TestDataPreparator implements TestDataPersister {

	@PersistenceContext(name="docum")
	private EntityManager entityManager;
	
	@Autowired
	private CryptoService cryptoService;
	
	@SuppressWarnings(value="unused")
	@Test
	public void prepareData(){
		AllDataPreparator preparator = new AllDataPreparator(this);
		preparator.prepareAllData();
		List<SecurityRole> securityRoles = prepareRoles();
		List<SecurityUser> users = prepareUsers(securityRoles);
	}
	
	private List<SecurityRole> prepareRoles() {
		List<SecurityRole> result = new ArrayList<SecurityRole>();
		result.add(new SecurityRole(SecurityRoleEnum.SUPERUSER));
		result.add(new SecurityRole(SecurityRoleEnum.DEVELOPER));
		result.add(new SecurityRole(SecurityRoleEnum.USER));
		result.add(new SecurityRole(SecurityRoleEnum.GUEST));
		persist(result);
		return result;
	}
	
	private List<SecurityUser> prepareUsers(List<SecurityRole> roles) {
		List<SecurityUser> result = new ArrayList<SecurityUser>();
		List<SecurityRole> adminRoles = new ArrayList<SecurityRole>();
		adminRoles.add(roles.get(0));
		List<SecurityRole> developerRoles = new ArrayList<SecurityRole>();
		developerRoles.add(roles.get(0));
		developerRoles.add(roles.get(1));
		List<SecurityRole> userRoles = new ArrayList<SecurityRole>();
		userRoles.add(roles.get(2));
		List<SecurityRole> guetsRoles = new ArrayList<SecurityRole>();
		guetsRoles.add(roles.get(3));
		result.add(new SecurityUser("admin", cryptoService.encryptText("admin"), adminRoles));
		result.add(new SecurityUser("dev", cryptoService.encryptText("dev"), developerRoles));
		result.add(new SecurityUser("user", cryptoService.encryptText("user"), userRoles));
		result.add(new SecurityUser("guest", "", guetsRoles));
		persist(result);
		return result;
	}
	

	@Override
	public <T extends IdentifiedEntity> void persist(T entity) {
		entityManager.persist(entity);
	}

	@Override
	public <T extends IdentifiedEntity> void persist(Collection<T> entities) {
		for(T entity : entities){
			persist(entity);
		}
	}

	public static class ContainerStateEnumCounter extends TestDataEnumCounter<ContainerStateEnum> {
		ContainerStateEnumCounter() {
			//super(ContainerStateEnum.values(), ContainerStateEnum.ABANDONED);
			super(ContainerStateEnum.values());
		}
	}
}
