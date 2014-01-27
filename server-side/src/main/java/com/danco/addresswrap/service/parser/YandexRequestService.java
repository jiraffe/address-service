package com.danco.addresswrap.service.parser;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.danco.addresswrap.domain.Address;

@Component
public class YandexRequestService extends RequestService	{

	private static final String URL = "http://geocode-maps.yandex.ru/1.x/?format=json&geocode=";
	private static final String POSITION_NODE_KEY = "{\"pos\":";
	
	@Override
	public String sendRequest() throws IOException {
		serviceUrl = URL + RequestService.question;
		return super.sendRequest();
	}

	@Override
	public Address parseResponse(String serviceResponse) {
		
		int posIndex = serviceResponse.indexOf(POSITION_NODE_KEY);
		int endPosNodeIndex = serviceResponse.indexOf("}", posIndex);
		
		String posNode = serviceResponse.substring(posIndex + POSITION_NODE_KEY.length() + 1, endPosNodeIndex - 1);
		
		String[] latLong = posNode.split(" ");
		
		Address addr = new Address();
		addr.setLongitude(latLong[0]);
		addr.setLatitude(latLong[1]);
		return addr;
	}
	
}