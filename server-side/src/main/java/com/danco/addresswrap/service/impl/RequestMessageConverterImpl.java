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
	
	private static final String SPACE = " ";
	private static final String STREET_SUFIXES = "улица ул.";
	private static Map<String, String> replacesMap = new HashMap<String, String>();
	
	private static final Set<String> CITIES = new HashSet<String>();
	
	private String targetCity;
	private String[] addressNodes;
	
	/**
	 * fill cities list
	 * currently hardcode Grodno
	 */
	static {
		CITIES.add("гродно");
		
		replacesMap.put("блк", "бульвар ленинского комсомла");
	}
	
	public Address parseRequest(String request) throws Exception {
		
		for(String sufix: STREET_SUFIXES.split(SPACE))	{
			request = request.replace(sufix, "");
		}
		
		request = request.replace("  ", "");
		
		addressNodes = request.toLowerCase().trim().split(SPACE);
		
		for (int i = 0; i < addressNodes.length; i++) {
			processNode(addressNodes[i], i);
		}
		
		Address address = addressService.getAddress(targetCity, addressNodes[1], addressNodes[2]);
		
		if(address != null)	{
			return address;
		}
		
		RequestService.question = targetCity + " " + addressNodes[1] + " " + addressNodes[2];
		
		address = unregistredProcessor.processServiceChain();
		address.setCity(targetCity);
		addressService.saveAddress(address);
		
		return address;
	}

	private void processNode(String node, int index) {
		if(CITIES.contains(node))	{
			targetCity = node;
		}
		
		if(replacesMap.containsKey(node))	{
			addressNodes[index] = replacesMap.get(node);
		}
	}

	@Override
	public Address parseRequest(String city, String street, String building) throws AddressNotFoundException, IOException, ParseException {
		
		Address address = addressService.getAddress(city, street, building);
		
		if(address != null)	{
			return address;
		}
		
		//if address not found then try to find synonims
		// TODO: process synonims
		
		RequestService.question = city + "+" + street + "+" + building;
		
		address = unregistredProcessor.processServiceChain();
		
		if(address == null) throw new AddressNotFoundException();
		
		addressService.saveAddress(address);
		
		return address;
	}	
}
