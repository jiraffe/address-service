package com.danco.addresswrap.dao;

import java.io.Serializable;

import com.danco.addresswrap.domain.Synonym;

public interface SynonymDao {

	public Serializable saveSynonym(Synonym synonym);
	
}
