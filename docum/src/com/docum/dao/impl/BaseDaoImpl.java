package com.docum.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.docum.dao.BaseDao;
import com.docum.persistence.DocumEntity;

public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {
	
	public <T extends DocumEntity> Long saveObject(T object) {
		Long id = null;

    	Session session = getSession();
        Transaction tx = session.beginTransaction();
    	session.saveOrUpdate(object);
        tx.commit();
        session.close();

		id = object.getId();
		return id;
	}
	
	public <T extends DocumEntity> void deleteObject(T object) {
    	Session session = getSession();
        Transaction tx = session.beginTransaction();
    	session.delete(object);
        tx.commit();
        session.close();
	}

}
