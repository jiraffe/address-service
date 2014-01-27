package com.danco.addresswrap.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.danco.addresswrap.domain.Address;
import com.danco.addresswrap.exception.AddressNotFoundException;
import com.danco.addresswrap.service.AddressService;
import com.danco.addresswrap.service.RequestMessageConverter;

@Controller
public class AddressRequestController {
	
	@Autowired
	private RequestMessageConverter messageConverter;

	@Autowired
	private AddressService addressService;
	
	private static final String PARSING_ERROR_MSG = "Error in parsing";
	private static final String ADDRESS_NOT_FOUND_MSG = "Address not found exception";
	private static final String CHECKING_SERVICES_NOT_AVALIABLE = "One or more services for checking address are not avaliable";
	
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/check", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> getAddress(@RequestParam(value="city") String city, @RequestParam(value="street") String street, @RequestParam(value="building") String building) {
		
		if((city == null || city.length() == 0) || (street == null || street.length() == 0) || (building == null || building.length() == 0))	{
			return mapError("Check address parameters");
		}
		
		Address address;
		try {
			address = messageConverter.parseRequest(city, street, building);
			return mapSuccess(address);
		} catch (ParseException e) {
			return mapError(PARSING_ERROR_MSG);
		} catch (AddressNotFoundException e) {
			return mapError(ADDRESS_NOT_FOUND_MSG);
		} catch (IOException e) {
			return mapError(CHECKING_SERVICES_NOT_AVALIABLE);
		}
    }
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addAddress(@RequestBody String data) {
		
		JSONObject parsedAddress;
		try {
			JSONParser parser = new JSONParser();
			parsedAddress = (JSONObject)parser.parse(data);
		} catch (ParseException e) {
			return mapError(ADDRESS_NOT_FOUND_MSG);
		}
		
		addressService.saveAddress(parsedAddress);
		
		return mapSuccess(data);
	}
	
	
	/**
	 * Map error.
	 *
	 * @param msg the msg
	 * @return the map
	 */
	public static Map<String,Object> mapError(String msg){

		Map<String,Object> modelMap = new HashMap<String,Object>(2);
		modelMap.put("message", msg);
		modelMap.put("success", false);

		return modelMap;
	} 
	
	/**
	 * Map success.
	 *
	 * @param data the list
	 * @param msg the msg
	 * @return the map
	 */
	public static Map<String, Object> mapSuccess(Object data) {
		
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("data", data);
		modelMap.put("success", true);
		return modelMap;
		
	}
}