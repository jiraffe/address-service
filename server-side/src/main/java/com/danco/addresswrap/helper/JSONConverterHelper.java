package com.danco.addresswrap.helper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.danco.addresswrap.domain.Address;

public class JSONConverterHelper {
	
	private static final Object LATITUDE_KEY = "latitude";
	private static final Object LONGITUDE_KEY = "longtitude";
	private static final Object CITY_KEY = "city";
	private static final Object BUILDING_KEY = "building";
	private static final Object COUNTRY_KEY = "country";
	private static final Object STATE_KEY = "state";
	private static final Object STREET_KEY = "street";
	
	public static JSONParser parser = new JSONParser();
	
	public static Address getAddressFromJSON(String jsonString) throws ParseException	{
		
		JSONObject parsedAddress;
		parsedAddress = (JSONObject)parser.parse(jsonString);
		
		return getAddressFromJSON(parsedAddress);
	}
	
	public static Address getAddressFromJSON(JSONObject jsonObject)	{
		Address address = new Address();
		
		address.setLatitude((String) jsonObject.get(LATITUDE_KEY));
		address.setLongitude((String) jsonObject.get(LONGITUDE_KEY));
		address.setCity((String) jsonObject.get(CITY_KEY));
		address.setBuilding((String) jsonObject.get(BUILDING_KEY));
		address.setCountry((String) jsonObject.get(COUNTRY_KEY));
		address.setState((String) jsonObject.get(STATE_KEY));
		address.setStreet(((String) jsonObject.get(STREET_KEY)));
		
		return address;
	}
	
}
