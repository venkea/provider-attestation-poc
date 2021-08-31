package com.hcsc.provider.attestation.processor;



import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.internal.command.CommandFactory;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.web.client.RestTemplate;

import com.hcsc.provider.attestation.common.CommonConstants;
import com.hcsc.provider.attestation.dao.AttestationDao;
import com.hcsc.provider.attestation.dao.ProviderLogsDao;
import com.hcsc.provider.attestation.model.Provider;
import com.hcsc.provider.attestation.model.ProviderLogs;

public class ProviderAttestationProcessor implements ItemProcessor<Provider, Provider> {

	private static final Logger log = LoggerFactory.getLogger(ProviderAttestationProcessor.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	AttestationDao attestationDao;
	
	@Autowired
	ProviderLogsDao providerLogsDao ;
	
	@Value("${url}")
	String url;

	@Value("${user}")
	String user;

	@Value("${password}")
	String password;

	private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;
	
	private static String CONTAINER_ID = "attestation.model_1.0.0-SNAPSHOT";
	
	private static String CLASS_NAME = "Provider";
	
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
				
				// set headers
//				String plainCreds = "User1:admin@123";
//				byte[] plainCredsBytes = plainCreds.getBytes();
//				byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
//				//String base64Creds = new String(base64CredsBytes);
//
//				HttpHeaders headers = new HttpHeaders();
//			//	encodedAuth = Base64.getEncoder().encodeToString(auth.toByteArray(Charset.forName("UTF-8")));
//				headers.add("Authorization", "Basic " + new String(base64CredsBytes));
//				headers.setContentType(MediaType.APPLICATION_JSON);
//				
//				HttpEntity<Provider> entity = new HttpEntity<>(provider, headers);
//				 
//			    //ResponseEntity<String> result = restTemplate.postForEntity(url, provider, String.class);
//				ResponseEntity<Provider> loginResponse = restTemplate
//						  .exchange(url, HttpMethod.POST, entity, Provider.class);
//				
//				//Verify request succeed
//				System.out.println("Status ::::: "+loginResponse.getStatusCodeValue());
//				System.out.println("Response::"+loginResponse.getBody());
				
				KieServicesConfiguration kieConfig = KieServicesFactory.newRestConfiguration(url, user, password);
				kieConfig.setMarshallingFormat(FORMAT);
				
				RuleServicesClient kieClient = KieServicesFactory.newKieServicesClient(kieConfig)
						.getServicesClient(RuleServicesClient.class);
				List<Command<?>> cmds = new ArrayList<>();
				KieCommands kieCommands = KieServices.Factory.get().getCommands();
				
				cmds.add(kieCommands.newInsert(provider, CLASS_NAME));
				cmds.add(kieCommands.newFireAllRules());
				
				BatchExecutionCommand batchCommand = CommandFactory.newBatchExecution(cmds);
				ServiceResponse<ExecutionResults> response = kieClient.executeCommandsWithResults(
						CONTAINER_ID, batchCommand);
				
				Provider updatedProvider = (Provider) response.getResult().getValue(CLASS_NAME);
				
				//provider = StatelessProviderValidation.execute(provider); 
				if (updatedProvider.getStatus().equals("FAILED")) {
					log.error("Provider with provider ID " + provider.getProviderId() + "is having : " +
							updatedProvider.getErrorDescription());
					
				//Error logs logic
				//Persisting into provider_logs table
				BigInteger jobExecutionId=attestationDao.getJobExecutionId();
				System.out.println("===============jobExecutionId================"+attestationDao.getJobExecutionId());
				ProviderLogs providerLogs=new ProviderLogs();
			//	providerLogs.setId(101);
				providerLogs.setProviderId(updatedProvider.getProviderId());
				providerLogs.setJobExecutionId(jobExecutionId);
				providerLogs.setDescription(updatedProvider.getErrorDescription());
				System.out.println(providerLogs);

				providerLogsDao.createOrUpdateProviderLogs(providerLogs);
				System.out.println("================providerLogs============");
				System.out.println("======================================");
				//	System.out.println(providerLogss);
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

	private boolean isLicenseExpired(String license) throws IllegalArgumentException {
		Date licenseDate;
		try {
			licenseDate = new SimpleDateFormat("mm/dd/yyyy").parse(license);
			if (licenseDate.before(new Date())) {
				throw new IllegalArgumentException("Provider with license" + licenseDate.getTime() + "has invalid data");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
