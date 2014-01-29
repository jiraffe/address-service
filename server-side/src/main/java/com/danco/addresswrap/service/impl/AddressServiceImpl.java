package com.danco.addresswrap.service.impl;

import java.io.Serializable;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danco.addresswrap.dao.AddressDao;
import com.danco.addresswrap.domain.Address;
import com.danco.addresswrap.helper.JSONConverterHelper;
import com.danco.addresswrap.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressDao dao;
	
	@Override
	@Transactional
	public Serializable saveAddress(Address address) {
		return dao.saveAddress(address);
	}

	@Override
	@Transactional
	public Address getAddress(String city, String street, String building) {
		return dao.getAddress(city, street, building);
	}

	@Override
	@Transactional
	public Address saveAddress(JSONObject parsedAddress) {
		
		Address address = JSONConverterHelper.getAddressFromJSON(parsedAddress);
		
		Address addressFromDb = getAddress(address.getCity(), address.getStreet(), address.getBuilding());
		
		if (addressFromDb == null) {
			this.saveAddress(address);
			return address;
		}
		
		return addressFromDb;
	}

	@Override
	@Transactional
	public Address getAddresBySynonimAndCity(String city, String synonim) {
		return dao.getAddressBySynonim(city, synonim);
	}

}
