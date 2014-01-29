package com.danco.addresswrap.decorator.impl;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.danco.addresswrap.decorator.AddressDecorator;
import com.danco.addresswrap.domain.Address;
import com.danco.addresswrap.exception.AddressNotFoundException;
import com.danco.addresswrap.service.AddressService;
import com.danco.addresswrap.service.impl.UnregistredAddressProcessor;
import com.danco.addresswrap.service.parser.RequestService;


/**
 * 
 * @author dzmitry_dubrovin
 * 	
 */

@Service
@Scope("singleton")
public class AddressDecoratorImpl implements AddressDecorator {

	@Autowired
	private AddressService addressService;
	
	@Autowired
	UnregistredAddressProcessor unregistredProcessor;

	@Override
	public Address getAddress(String city, String street, String building) throws AddressNotFoundException, IOException, ParseException {
		
		Address address = addressService.getAddress(city, street, building);
		
		if(address != null)	{
			return address;
		}
		
		RequestService.question = city + "+" + street + "+" + building;
		
		address = unregistredProcessor.processServiceChain();
		
		if(address == null) throw new AddressNotFoundException();
		
		addressService.saveAddress(address);
		
		return address;
	}

	@Override
	public Address getAddressBySynonym(String city, String synonym) throws AddressNotFoundException {
		
		Address address = addressService.getAddresBySynonimAndCity(city, synonym);
		
		if(address == null) throw new AddressNotFoundException();
		
		return address;
	}	
}
