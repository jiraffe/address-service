package com.danco.addresswrap.dao.impl;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danco.addresswrap.dao.SynonymDao;
import com.danco.addresswrap.domain.Synonym;

@Repository
public class SynonymDaoImpl implements SynonymDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Serializable saveSynonym(Synonym synonym) {
		return sessionFactory.getCurrentSession().save(synonym);
	}
}
