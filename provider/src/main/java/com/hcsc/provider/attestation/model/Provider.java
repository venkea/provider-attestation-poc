package com.hcsc.provider.attestation.model;

import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class Provider {

	private Integer providerId;

	private String providerType;

	private String providerFirstName;

	private String providerLastName;

	private String taxId;

	private String npi;

	private String ssn;

	private Date licenseExpiryDate;

	private String addressLine1;

	private String state;

	private Integer zipCode;

	private String providerSpecialityCode;
	
	@OneToOne
	@JoinColumn(name="provider_id")
	private ProviderDetails details;

	
	
	public Provider(Integer providerId, String providerType, String providerFirstName, String providerLastName,
			String taxId, String npi, String ssn, Date licenseExpiryDate, String addressLine1, String state,
			Integer zipCode, String providerSpecialityCode, ProviderDetails details) {
		super();
		this.providerId = providerId;
		this.providerType = providerType;
		this.providerFirstName = providerFirstName;
		this.providerLastName = providerLastName;
		this.taxId = taxId;
		this.npi = npi;
		this.ssn = ssn;
		this.licenseExpiryDate = licenseExpiryDate;
		this.addressLine1 = addressLine1;
		this.state = state;
		this.zipCode = zipCode;
		this.providerSpecialityCode = providerSpecialityCode;
		this.details = details;
	}

	public Provider() {
		super();
	}

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
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

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getNpi() {
		return npi;
	}

	public void setNpi(String npi) {
		this.npi = npi;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public Date getLicenseExpiryDate() {
		return licenseExpiryDate;
	}

	public void setLicenseExpiryDate(Date licenseExpiryDate) {
		this.licenseExpiryDate = licenseExpiryDate;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	public String getProviderSpecialityCode() {
		return providerSpecialityCode;
	}

	public void setProviderSpecialityCode(String providerSpecialityCode) {
		this.providerSpecialityCode = providerSpecialityCode;
	}

	public ProviderDetails getDetails() {
		return details;
	}

	public void setDetails(ProviderDetails details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "Provider [providerId=" + providerId + ", providerType=" + providerType + ", providerFirstName="
				+ providerFirstName + ", providerLastName=" + providerLastName + ", taxId=" + taxId + ", npi=" + npi
				+ ", ssn=" + ssn + ", licenseExpiryDate=" + licenseExpiryDate + ", addressLine1=" + addressLine1
				+ ", state=" + state + ", zipCode=" + zipCode + ", providerSpecialityCode=" + providerSpecialityCode
				+ ", details=" + details + "]";
	}
	
	
	
	

}
