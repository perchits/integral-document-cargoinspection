package test.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.docum.dao.VoyageDao;
import com.docum.persistence.common.Container;
import com.docum.persistence.common.Voyage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/docum-context.xml")
@Transactional
public class VoyageDaoTest {
	@Autowired
	VoyageDao voyageDao;
	
	@Test
	public void testGetVoyages() {
		voyageDao.getAll(Voyage.class);
		voyageDao.getVoyagesByFinishStatus(true);
		List<Voyage> voyages = voyageDao.getVoyagesByFinishStatus(false);
		assertTrue(voyages.size() > 0);
		Voyage v = voyages.get(0);
		assertTrue(v.getContainers().size() > 0);
		for(Container c : v.getContainers()) {
			assertNotNull(c);
		}
	}
}
