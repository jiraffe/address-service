package com.danco.addresswrap.service.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.parser.ParseException;

import com.danco.addresswrap.domain.Address;
import com.danco.addresswrap.exception.AddressNotFoundException;

public abstract class RequestService {

	public static String question;
	protected String serviceUrl;

	private String REQUEST_METHOD = "GET";

	public String sendRequest() throws IOException {
		
		URL url = new URL(serviceUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		
		con.setRequestMethod(REQUEST_METHOD);
		int responseCode = con.getResponseCode();

		System.out.println("responseCode:	" + responseCode);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		return response.toString();
	}

	public void setRequestMethod(String requestMethod) {
		REQUEST_METHOD = requestMethod;
	}
	public abstract Address parseResponse(String serviceResponse) throws ParseException, AddressNotFoundException;
}
