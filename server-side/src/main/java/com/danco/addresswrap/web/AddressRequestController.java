package com.danco.addresswrap.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/check", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> getAddress(@RequestParam(value="city") String city, @RequestParam(value="street") String street, @RequestParam(value="building") String building) {
		
		if(city == null || street == null || building == null)	{
			return mapError("Check address parameters");
		}
		
		Address address;
		try {
			address = messageConverter.parseRequest(city, street, building);
			return mapSuccess(address);
		} catch (ParseException e) {
			return mapError("Error in parsing");
		} catch (AddressNotFoundException e) {
			return mapError("Address not found exception");
		} catch (IOException e) {
			return mapError("One or more services for checking address are not avaliable");
		}
    }
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addAddress(@RequestBody Object data) {
		System.out.println(data);
		return null;
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