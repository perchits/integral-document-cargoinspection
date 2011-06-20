package com.docum.test.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import com.docum.dao.BaseDao;
import com.docum.dao.VoyageDao;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Vessel;
import com.docum.domain.po.common.Voyage;
import com.docum.test.TestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/docum-context.xml")
@Transactional
public class TestVoyageDao {
	@Autowired
	VoyageDao voyageDao;
	@Autowired
	BaseDao baseDao;
	
	@Test
	public void testGetVoyages() {
		voyageDao.getAll(Voyage.class, null);
		voyageDao.getVoyagesByFinishStatus(true);
		List<Voyage> voyages = voyageDao.getVoyagesByFinishStatus(false);
		assertTrue(voyages.size() > 0);
		Voyage v = voyages.get(0);
		assertTrue(v.getContainers().size() > 0);
		for(Container c : v.getContainers()) {
			assertNotNull(c);
		}
	}
	
	@Test
	public void testSaveVoyage() {
		Voyage voyage = new Voyage();
		List<Vessel> vessels = baseDao.getAll(Vessel.class, null);
		assertTrue(vessels.size() > 0);
		voyage.setVessel(vessels.get(0));
		voyage.setNumber(TestUtil.getRandomString(8));
		voyage = voyageDao.save(voyage);
		assertTrue(voyage.getId() != null);
	}
}
