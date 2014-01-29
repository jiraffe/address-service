package com.danco.addresswrap.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danco.addresswrap.dao.SynonymDao;
import com.danco.addresswrap.domain.Address;
import com.danco.addresswrap.domain.Synonym;
import com.danco.addresswrap.service.SynonymService;

@Service
public class SynonymServiceImpl implements SynonymService {

	@Autowired
	private SynonymDao dao;

	@Override
	@Transactional
	public Serializable saveSynonym(Synonym synonym) {
		return dao.saveSynonym(synonym);
	}

	@Override
	@Transactional
	public Serializable saveSynonym(String synonym, Address address) {
		
		Synonym s = new Synonym();
		s.setSynonymKey(synonym);
		s.setAddress(address);
		
		return this.saveSynonym(s);
	}

	@Override
	@Transactional
	public void saveAddressSynonims(Address address, String synonyms) {
		
		String[] synArrs = synonyms.split(",");
		
		for (String strSyn : synArrs) {
			Synonym synonym = new Synonym();
			synonym.setAddress(address);
			synonym.setSynonymKey(strSyn);
			
			dao.saveSynonym(synonym);
		}
	}
}