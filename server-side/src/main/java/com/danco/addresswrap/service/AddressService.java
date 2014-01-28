package com.danco.addresswrap.service;

import java.io.Serializable;

import org.json.simple.JSONObject;

import com.danco.addresswrap.domain.Address;

public interface AddressService {

	public Serializable saveAddress(Address address);

	public Address getAddress(String city, String street, String building);

	public Address saveAddress(JSONObject parsedAddress);
	
	public Address getAddresBySynonimAndCity(String city, String synonim);
}
