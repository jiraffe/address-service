package com.danco.addresswrap.service.impl;

import java.io.Serializable;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danco.addresswrap.dao.SynonymDao;
import com.danco.addresswrap.domain.Address;
import com.danco.addresswrap.domain.Synonym;
import com.danco.addresswrap.service.SynonymService;

@Service
public class SynonymServiceImpl implements SynonymService {

	@Autowired
	private SynonymDao dao;

	@Override
	public Serializable saveSynonym(Synonym synonym) {
		return dao.saveSynonym(synonym);
	}

	@Override
	public Serializable saveSynonym(String synonym, Address address) {
		
		Synonym s = new Synonym();
		s.setSynonymKey(synonym);
		s.setAddress(address);
		
		return this.saveSynonym(s);
	}

	@Override
	public void saveAddressSynonims(Address address, JSONArray synonyms) {
		
		
		
	}

}
