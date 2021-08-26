package com.hcsc.provider.attestation.model;

public class Jewellery {

	private String offer;
	
	private String type;
	
	
	public Jewellery() {
		// TODO Auto-generated constructor stub
	}

	public Jewellery(String offer, String type) {
		super();
		this.offer = offer;
		this.type = type;
	}

	public String getOffer() {
		return offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Jewellery [offer=" + offer + ", type=" + type + "]";
	}
	
	
	
}
