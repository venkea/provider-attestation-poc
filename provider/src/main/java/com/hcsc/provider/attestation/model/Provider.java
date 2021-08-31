package com.hcsc.provider.attestation.model;

import java.util.Date;

public class Provider {

	private Integer providerId;

	private String providerFirstName;

	private String providerLastName;

	private String addressType;

	private String addressLine1;

	private String addressLine2;
	
	private String city;
	
	private String state;

	private String region;

	private String zipCode;
	
	private String phoneNumber;

	private String emailId;
	
	private String npi;
	
	private String taxId;

	private String deaId;

	private String ssn;

	private String ein;

	private String providerSpecialityCode;

	private String licenceState;

	private String licenceNumber;

	private String licenseExpiryDate;

	private String providerPracticeState;

	private String product;

	private String provType;

	private Date provEffectiveDate;

	private String provEffectiveStatus;

	private String status;

	private String errorDescription;
	
	private String networkRequested;
	
	private String siteId;
	
	private String compCode;
	
	private String serviceCounty;

	public String getNetworkRequested() {
		return networkRequested;
	}

	public void setNetworkRequested(String networkRequested) {
		this.networkRequested = networkRequested;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getCompCode() {
		return compCode;
	}

	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}

	public String getServiceCounty() {
		return serviceCounty;
	}

	public void setServiceCounty(String serviceCounty) {
		this.serviceCounty = serviceCounty;
	}

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public String getProviderFirstName() {
		return providerFirstName;
	}

	public void setProviderFirstName(String providerFirstName) {
		this.providerFirstName = providerFirstName;
	}

	public String getProviderLastName() {
		return providerLastName;
	}

	public void setProviderLastName(String providerLastName) {
		this.providerLastName = providerLastName;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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

	public String getNpi() {
		return npi;
	}

	public void setNpi(String npi) {
		this.npi = npi;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getDeaId() {
		return deaId;
	}

	public void setDeaId(String deaId) {
		this.deaId = deaId;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getEin() {
		return ein;
	}

	public void setEin(String ein) {
		this.ein = ein;
	}

	public String getProviderSpecialityCode() {
		return providerSpecialityCode;
	}

	public void setProviderSpecialityCode(String providerSpecialityCode) {
		this.providerSpecialityCode = providerSpecialityCode;
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

	public String getLicenseExpiryDate() {
		return licenseExpiryDate;
	}

	public void setLicenseExpiryDate(String licenseExpiryDate) {
		this.licenseExpiryDate = licenseExpiryDate;
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

	public String getProvType() {
		return provType;
	}

	public void setProvType(String provType) {
		this.provType = provType;
	}

	public Date getProvEffectiveDate() {
		return provEffectiveDate;
	}

	public void setProvEffectiveDate(Date provEffectiveDate) {
		this.provEffectiveDate = provEffectiveDate;
	}

	public String getProvEffectiveStatus() {
		return provEffectiveStatus;
	}

	public void setProvEffectiveStatus(String provEffectiveStatus) {
		this.provEffectiveStatus = provEffectiveStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	@Override
	public String toString() {
		return "Provider [providerId=" + providerId + ", providerFirstName=" + providerFirstName + ", providerLastName="
				+ providerLastName + ", addressType=" + addressType + ", addressLine1=" + addressLine1
				+ ", addressLine2=" + addressLine2 + ", city=" + city + ", state=" + state + ", region=" + region
				+ ", zipCode=" + zipCode + ", phoneNumber=" + phoneNumber + ", emailId=" + emailId + ", npi=" + npi
				+ ", taxId=" + taxId + ", deaId=" + deaId + ", ssn=" + ssn + ", ein=" + ein
				+ ", providerSpecialityCode=" + providerSpecialityCode + ", licenceState=" + licenceState
				+ ", licenceNumber=" + licenceNumber + ", licenseExpiryDate=" + licenseExpiryDate
				+ ", providerPracticeState=" + providerPracticeState + ", product=" + product + ", provType=" + provType
				+ ", provEffectiveDate=" + provEffectiveDate + ", provEffectiveStatus=" + provEffectiveStatus + "]";
	}

}
