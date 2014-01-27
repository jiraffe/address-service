package com.danco.addresswrap.service.parser;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import com.danco.addresswrap.domain.Address;
import com.danco.addresswrap.exception.AddressNotFoundException;

@Component
public class OSMReqestService extends RequestService {

	private static final String URL = "http://nominatim.openstreetmap.org/search.php?addressdetails=1&format=json&q=";
	private static final CharSequence STREET_STUB = "улица ";
	private static final Object LATITUDE_KEY = "lat";
	private static final Object LONGITUDE_KEY = "lon";
	private static final Object ADDRESS_KEY = "address";
	private static final Object CITY_KEY = "city";
	private static final Object HOUSE_NUMBER_KEY = "house_number";
	private static final Object COUNTRY_KEY = "country";
	private static final Object STATE_KEY = "state";
	private static final Object ROAD_KEY = "road";
	private static final String SPACE_STUB = " ";
	private static final String ESCAPE_CHAR = "+";
	private static final CharSequence EMPTY_CHAR = "";
	
	
	@Override
	public String sendRequest() throws IOException {
		serviceUrl = URL + question.substring(0, 1).toUpperCase() + question.substring(1);
		serviceUrl = serviceUrl.replaceAll(SPACE_STUB, ESCAPE_CHAR);
		return super.sendRequest();
	}
	
	@Override
	public Address parseResponse(String serviceResponse) throws ParseException, AddressNotFoundException {
		
		JSONParser parser = new JSONParser();
		JSONArray parsedJson = (JSONArray)parser.parse(serviceResponse);
		
		if(parsedJson.size() > 1)	{
			throw new AddressNotFoundException();
		}

		JSONObject returnedAddress = (JSONObject) parsedJson.get(0);
		JSONObject parsedAddress = (JSONObject) returnedAddress.get(ADDRESS_KEY);
		
		Address addr = new Address();
		
		addr.setLatitude((String) returnedAddress.get(LATITUDE_KEY));
		addr.setLongitude((String) returnedAddress.get(LONGITUDE_KEY));
		addr.setCity((String) parsedAddress.get(CITY_KEY));
		addr.setBuilding((String) parsedAddress.get(HOUSE_NUMBER_KEY));
		addr.setCountry((String) parsedAddress.get(COUNTRY_KEY));
		addr.setState((String) parsedAddress.get(STATE_KEY));
		addr.setStreet(((String) parsedAddress.get(ROAD_KEY)).replace(STREET_STUB, EMPTY_CHAR));
		
		return addr;
	}
}
