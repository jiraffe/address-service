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
import com.danco.addresswrap.helper.JSONConverterHelper;
import com.danco.addresswrap.service.AddressService;
import com.danco.addresswrap.service.RequestMessageConverter;
import com.danco.addresswrap.service.SynonymService;

import static org.springframework.util.StringUtils.isEmpty;

@Controller
public class AddressRequestController {
	
	@Autowired
	private RequestMessageConverter messageConverter;

	@Autowired
	private AddressService addressService;
	
	@Autowired
	private SynonymService synonymService;
	
	//error messages
	private static final String PARSING_ERROR_MSG = "Error in parsing";
	private static final String ADDRESS_NOT_FOUND_MSG = "Address not found exception";
	private static final String CHECKING_SERVICES_NOT_AVALIABLE = "One or more services for checking address are not avaliable";
	private static final String PARAMS_NOT_FILLED = "Check address parameters";

	//response object messages 
	private static final String MESSAGE_KEY = "message";
	private static final String SUCCESS_KEY = "success";
	private static final String DATA_KEY = "data";

	//mapping
	private static final String CHECK_URL = "/check";
	private static final String ADD_URL = "/add";

	//JSON keys
	private static final String SYNONYMS_KEY = "synonyms";
	
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value=CHECK_URL, method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> getAddress(
				@RequestParam(value="city") String city, 
				@RequestParam(value="street") String street, 
				@RequestParam(value="building") String building, 
				@RequestParam(value="synonym") String synonym
    	) {
		
		
		if(isEmpty(city) && isEmpty(synonym))	{
			return mapError(PARAMS_NOT_FILLED);
		}
		
		if(isEmpty(city) && isEmpty(street) && isEmpty(building))	{
			return mapError(PARAMS_NOT_FILLED);
		}
		
		Address address;
		try {
			address = messageConverter.parseRequest(city, street, building, synonym);
			return mapSuccess(address);
		} catch (ParseException e) {
			return mapError(PARSING_ERROR_MSG);
		} catch (AddressNotFoundException e) {
			return mapError(ADDRESS_NOT_FOUND_MSG);
		} catch (IOException e) {
			return mapError(CHECKING_SERVICES_NOT_AVALIABLE);
		}
    }
	
	/**
	 * parse JSON data
	 * get POSTed address
	 * check address in db
	 * 
	 * if not exists - add them
	 * 
	 * check if synonyms are presented
	 * collect which synonyms are not stored
	 * 
	 * store them
	 * 
	 * return success map
	 */
	@RequestMapping(value=ADD_URL, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addAddress(@RequestBody String data) {
		
		//parse JSON data
		JSONObject parsedObject;
		try {
			JSONParser parser = new JSONParser();
			parsedObject = (JSONObject)parser.parse(data);
		} catch (ParseException e) {
			return mapError(ADDRESS_NOT_FOUND_MSG);
		}
		//get POSTed address
		Address address = JSONConverterHelper.getAddressFromJSON(parsedObject);
		
		if((address.getCity() == null || address.getCity().length() == 0) || 
				(address.getStreet() == null || address.getStreet().length() == 0) || 
				(address.getBuilding() == null || address.getBuilding().length() == 0))	{
			return mapError(PARAMS_NOT_FILLED);
		}
		
		//check address in db
		Address addressFromDb = addressService.getAddress(address.getCity(), address.getStreet(), address.getBuilding());
		
		//if not exists - add them
		if (addressFromDb != null) {
			address = addressFromDb;
		} else {
			addressService.saveAddress(address);
		}
		
		//get synonyms
		String synonyms = (String) parsedObject.get(SYNONYMS_KEY);
		
		if(synonyms.length() > 0)	{
			synonymService.saveAddressSynonims(address, synonyms);
		}

		
		return mapSuccess(address);
	}
	
	/**
	 * Map error.
	 *
	 * @param msg the msg
	 * @return the map
	 */
	public static Map<String,Object> mapError(String msg){

		Map<String,Object> modelMap = new HashMap<String,Object>(2);
		modelMap.put(MESSAGE_KEY, msg);
		modelMap.put(SUCCESS_KEY, false);

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
		modelMap.put(DATA_KEY, data);
		modelMap.put(SUCCESS_KEY, true);
		return modelMap;
		
	}
}