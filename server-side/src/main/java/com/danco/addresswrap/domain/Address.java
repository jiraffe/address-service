package com.danco.addresswrap.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Address")
public class Address {

	@Id
	@Column(name="address_id")
	@GeneratedValue
	private Integer id;
	
	@Column(name="country")
	private String country;
	
	@Column(name="state")
	private String state;
	
	@Column(name="city")
	private String city;
	
	@Column(name="street")
	private String street;
	
	@Column(name="latitude")
	private String latitude;

	@Column(name="longitude")
	private String longitude;
	
	@Column(name="building")
	private String building;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "address")
	private List<Synonym> synonims;
	
	public Address(String country, String state, String city, String street, String building) {
		super();
		this.country = country;
		this.state = state;
		this.city = city;
		this.street = street;
		this.building = building;
	}

	public Address() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public List<Synonym> getSynonims() {
		return synonims;
	}

	public void setSynonims(List<Synonym> synonims) {
		this.synonims = synonims;
	}
}
