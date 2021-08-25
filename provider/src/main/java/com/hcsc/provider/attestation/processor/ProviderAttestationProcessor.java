package com.hcsc.provider.attestation.processor;

import java.util.Date;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.hcsc.provider.attestation.common.CommonConstants;
import com.hcsc.provider.attestation.drools.StatelessProviderValidation;
import com.hcsc.provider.attestation.model.Provider;
import com.hcsc.provider.attestation.model.Validation;

public class ProviderAttestationProcessor implements ItemProcessor<Provider, Provider> {

	private static final Logger log = LoggerFactory.getLogger(ProviderAttestationProcessor.class);
	
	@Override
	public Provider process(Provider provider) {
		log.info("Inside Processor " + provider.getProviderId());
		return isValidated(provider) ? provider : null;
		
	}

	private boolean isValidated(Provider provider) {
		if(Objects.nonNull(provider)) {
			if (isValidSsn(provider.getSsn()) ||
					!CommonConstants.PROVIDERS_STATES.contains(provider.getState()) ||
					isLicenseExpired(provider.getLicenseExpiryDate()) ||
					checkInvalidAddress(provider.getAddressLine1()) ||
					isInvalidZipcode(provider.getZipCode())) {
				return false;
			} else {
				//Provider droolsProvider = new Provider();
				//BeanUtils.copyProperties(provider, droolsProvider);
				provider = StatelessProviderValidation.execute(provider); 
				if (provider.getStatus().equals(Validation.FAILED)){
					log.error("Provider with provider ID " + provider.getProviderId() + "is having : " +
							provider.getErrorDescription());
					return false;
				}
			}
		}
		return true;
	}

	private boolean isValidSsn(String ssn) throws IllegalArgumentException {
		if (ssn.isEmpty() || ssn.equals("-")) {
			throw new IllegalArgumentException("has invalid SSN '" + ssn + "'");
		}
		return false;
	}

	private boolean isLicenseExpired(Date license) throws IllegalArgumentException {
		if (license.before(new Date())) {
			throw new IllegalArgumentException("Provider with license" + license.getTime() + "has invalid data");
		}
		return false;
	}
	
	private boolean checkInvalidAddress(String address) throws IllegalArgumentException {
		if (address.isEmpty() || address.equals("-")) {
			throw new IllegalArgumentException("Provider with address " + address + "has invalid data");
		}
		return false;
	}

	private boolean isInvalidZipcode(String zipcode) throws IllegalArgumentException {
		if (zipcode.isEmpty() || zipcode.equals("-")) {
			throw new IllegalArgumentException("Provider with zipcode " + zipcode + "has invalid data");
		}
		return false;
	}

}
