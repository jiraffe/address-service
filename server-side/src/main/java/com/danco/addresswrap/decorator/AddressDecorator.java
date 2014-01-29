package com.danco.addresswrap.decorator;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.danco.addresswrap.domain.Address;
import com.danco.addresswrap.exception.AddressNotFoundException;

public interface AddressDecorator {

	public Address getAddress(String city, String street, String building) throws AddressNotFoundException, IOException, ParseException;

	public Address getAddressBySynonym(String city, String synonym) throws AddressNotFoundException;
	
}
