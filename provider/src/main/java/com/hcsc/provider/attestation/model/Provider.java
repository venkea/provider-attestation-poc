package com.hcsc.provider.attestation.model;


public class Provider {

	private String provID;
	private String provFirstNm;
	private String provLastNm;
	
	
	public Provider() {
		super();
	}

	public Provider(String provID, String provFirstNm, String provLastNm) {
		super();
		this.provID = provID;
		this.provFirstNm = provFirstNm;
		this.provLastNm = provLastNm;
	}
	public String getProvID() {
		return provID;
	}
	public void setProvID(String provID) {
		this.provID = provID;
	}
	public String getProvFirstNm() {
		return provFirstNm;
	}
	public void setProvFirstNm(String provFirstNm) {
		this.provFirstNm = provFirstNm;
	}
	public String getProvLastNm() {
		return provLastNm;
	}
	public void setProvLastNm(String provLastNm) {
		this.provLastNm = provLastNm;
	}
	
	
	
}
