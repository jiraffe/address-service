package com.danco.addresswrap.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.danco.addresswrap.domain.Address;
import com.danco.addresswrap.exception.AddressNotFoundException;
import com.danco.addresswrap.service.AddressService;
import com.danco.addresswrap.service.RequestMessageConverter;
import com.danco.addresswrap.service.parser.RequestService;


/**
 * 
 * @author dzmitry_dubrovin
 *
 * total process flow looks as follows:
 * 	- convert to lover-case
 *  - split predicates:
 *  	*city
 *  	*street
 *  	*building
 *  - made address_key
 *  - check if key exists in local DB
 *  - if yes - return lat\long from local DB
 *  - if not - made request to some services via API to get gps coords
 *  - store them in local DB
 *  - return return lat\long
 */

@Service
@Scope("singleton")
public class RequestMessageConverterImpl implements RequestMessageConverter {

	@Autowired
	private AddressService addressService;
	
	@Autowired
	UnregistredAddressProcessor unregistredProcessor;
	
	/**
	 * fill cities list
	 * currently hardcode Grodno
	 */

	@Override
	public Address parseRequest(String city, String street, String building, String synonyms) throws AddressNotFoundException, IOException, ParseException {
		
		Address address = addressService.getAddress(city, street, building);
		
		if(address != null)	{
			return address;
		}
		
		//if address not found then try to find synonims
		// TODO: process synonyms
		
		RequestService.question = city + "+" + street + "+" + building;
		
		address = unregistredProcessor.processServiceChain();
		
		if(address == null) throw new AddressNotFoundException();
		
		addressService.saveAddress(address);
		
		return address;
	}	
}
