package com.danco.addresswrap.service;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.danco.addresswrap.domain.Address;
import com.danco.addresswrap.exception.AddressNotFoundException;

public interface RequestMessageConverter {

	public Address parseRequest(String city, String street, String building, String synonyms) throws AddressNotFoundException, IOException, ParseException;
	
}
