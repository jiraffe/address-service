package com.danco.addresswrap.service.parser;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.danco.addresswrap.domain.Address;

@Component
public class OSM_RURequestService extends RequestService	{

	private static final String URL = "http://openstreetmap.ru/api/search?q=";
	
	
	@Override
	public String sendRequest() throws IOException {
		serviceUrl = URL + RequestService.question;
		return super.sendRequest();
	}

	@Override
	public Address parseResponse(String serviceResponse) {
		
		Address addr = new Address();
		return addr;
	}

}
