package com.danco.addresswrap.service;

import org.json.simple.JSONObject;

import com.danco.addresswrap.domain.Address;

public interface AddressService {

	public void saveAddress(Address address);

	Address getAddress(String city, String street, String building);

	public void saveAddress(JSONObject parsedAddress);
	
}
