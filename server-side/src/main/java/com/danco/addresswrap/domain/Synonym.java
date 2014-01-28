package com.danco.addresswrap.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Synonym")
@JsonAutoDetect
public class Synonym {

	@Id
	@Column(name="synonym_id")
	@GeneratedValue
	private Integer id;
	
	@Column(name="synonym_key")
	private String synonymKey;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	@JoinColumn(name = "address_id", nullable = false)
	private Address address;

	public Synonym(Integer id, String synonymKey) {
		super();
		this.id = id;
		this.synonymKey = synonymKey;
	}

	public Synonym() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSynonymKey() {
		return synonymKey;
	}

	public void setSynonymKey(String key) {
		this.synonymKey = key;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
