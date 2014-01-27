package com.danco.addresswrap.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.danco.addresswrap.domain.Address;
import com.danco.addresswrap.exception.AddressNotFoundException;
import com.danco.addresswrap.service.ApplicationContextService;
import com.danco.addresswrap.service.parser.OSMReqestService;
import com.danco.addresswrap.service.parser.RequestService;

@Service
@Scope("singleton")
public class UnregistredAddressProcessor {

	@Autowired
	ApplicationContextService contextService;
	
	
	public static List<RequestService> services = new ArrayList<>();
	
	@Autowired
	public void registerServices() {
		services.add((RequestService) contextService.getBean(OSMReqestService.class));
	}
	
	public Address processServiceChain() throws IOException, ParseException, AddressNotFoundException	{
		
		for (RequestService service : services) {
			String serviceResponse = service.sendRequest();
			Address address = service.parseResponse(serviceResponse);
			if(address != null)	{
				return address;
			}
		}
		
		return null;
	}
}
