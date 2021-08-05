package com.hcsc.provider.attestation.model;

public class ProviderDetails {

	private Integer providerId;

	private String deaId;

	private String ein;
	
	private String licenceState;

	private String licenceNumber;

	private String addressType;

	private String addressLine2;

	private String city;

	private String region;

	private String phoneNumber;

	private String emailId;

	private String providerPracticeState;

	private String product;

	public ProviderDetails() {
		super();
	}
	
	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public String getDeaId() {
		return deaId;
	}

	public void setDeaId(String deaId) {
		this.deaId = deaId;
	}

	public String getEin() {
		return ein;
	}

	public void setEin(String ein) {
		this.ein = ein;
	}

	public String getLicenceState() {
		return licenceState;
	}

	public void setLicenceState(String licenceState) {
		this.licenceState = licenceState;
	}

	public String getLicenceNumber() {
		return licenceNumber;
	}

	public void setLicenceNumber(String licenceNumber) {
		this.licenceNumber = licenceNumber;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getProviderPracticeState() {
		return providerPracticeState;
	}

	public void setProviderPracticeState(String providerPracticeState) {
		this.providerPracticeState = providerPracticeState;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "ProviderDetails [providerId=" + providerId + ", deaId=" + deaId + ", ein=" + ein + ", licenceState="
				+ licenceState + ", licenceNumber=" + licenceNumber + ", addressType=" + addressType + ", addressLine2="
				+ addressLine2 + ", city=" + city + ", region=" + region + ", phoneNumber=" + phoneNumber + ", emailId="
				+ emailId + ", providerPracticeState=" + providerPracticeState + ", product=" + product + "]";
	}
	
	

}
