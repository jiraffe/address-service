package com.danco.addresswrap.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danco.addresswrap.dao.AddressDao;
import com.danco.addresswrap.domain.Address;
import com.danco.addresswrap.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressDao dao;
	
	@Override
	@Transactional
	public void saveAddress(Address address) {
		dao.saveAddress(address);
	}

	@Override
	@Transactional
	public Address getAddress(String city, String street, String building) {
		return dao.getAddress(city, street, building);
	}

}
