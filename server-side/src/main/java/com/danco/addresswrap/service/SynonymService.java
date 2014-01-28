package com.danco.addresswrap.service;

import java.io.Serializable;

import org.json.simple.JSONArray;

import com.danco.addresswrap.domain.Address;
import com.danco.addresswrap.domain.Synonym;

public interface SynonymService {

	public Serializable saveSynonym(Synonym synonym);

	public Serializable saveSynonym(String synonym, Address address);

	public void saveAddressSynonims(Address address, JSONArray synonyms);
	
}
