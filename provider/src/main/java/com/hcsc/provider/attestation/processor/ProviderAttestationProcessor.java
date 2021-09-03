package com.hcsc.provider.attestation.processor;



import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import org.springframework.web.client.RestTemplate;

import com.hcsc.provider.attestation.common.CommonConstants;
import com.hcsc.provider.attestation.model.Provider;
import com.opencsv.CSVWriter;

public class ProviderAttestationProcessor implements ItemProcessor<Provider, Provider> {

	private static final Logger log = LoggerFactory.getLogger(ProviderAttestationProcessor.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${url}")
	String url;

	@Value("${user}")
	String user;

	@Value("${password}")
	String password;

	private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;
	
	private static String CONTAINER_ID = "attestation.model_1.0.0";
	
	private static String CLASS_NAME = "Provider";
	
	Provider updatedProvider = null;
	
	File file = new File("C:\\Users\\venkea\\Documents\\HCSC\\Roster\\Reports\\provider_");
	
	public static String QUERY_INSERT_PROVIDER;
	
	public static String QUERY_INSERT_PROVIDER_DETAILS;
	
	@Override
	public Provider process(Provider provider) {
		log.info("Inside Processor " + provider.getProviderId());
		
		if(isValidated(provider)) {
			return updatedProvider;
		} else {
			return null;
		}
		
	}

	private boolean isValidated(Provider provider) {
		if(Objects.nonNull(provider)) {
			if (isValidSsn(provider) ||
					!CommonConstants.PROVIDERS_STATES.contains(provider.getState()) ||
					isLicenseExpired(provider) ||
					checkInvalidAddress(provider) ||
					isInvalidZipcode(provider)) {
				return false;
			} else {
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
				
				updatedProvider = (Provider) response.getResult().getValue(CLASS_NAME);
				updatedProvider.setErrorDescription(updatedProvider.getErrorDescription().trim());
				//provider = StatelessProviderValidation.execute(provider); 
//				if (updatedProvider.getStatus().equals("FAILED")) {
//					log.error("FAILED--- Provider with provider ID " + updatedProvider.getProviderId() + "is having : " +
//							updatedProvider.getErrorDescription());
//					//Invalid records written to csv
//					//Create writer instance
//					writeToFile(updatedProvider);
//					return true;
//				}
			}
		}
		return true;
	}

	private boolean isValidSsn(Provider provider) throws IllegalArgumentException {
		if (provider.getSsn().isEmpty() || provider.getSsn().equals("-")) {
			writeToFile(provider);
			throw new IllegalArgumentException("has invalid SSN '" + provider.getSsn() + "'");
		}
		return false;
	}

	private boolean isLicenseExpired(Provider provider) throws IllegalArgumentException {
		Date licenseDate;
		try {
			licenseDate = new SimpleDateFormat("mm/dd/yyyy").parse(provider.getLicenseExpiryDate());
			if (licenseDate.before(new Date())) {
				writeToFile(provider);
				throw new IllegalArgumentException("Provider with license" + licenseDate.getTime() + "has invalid data");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		return false;
	}
	
	private boolean checkInvalidAddress(Provider provider) throws IllegalArgumentException {
		if (provider.getAddressLine1().isEmpty() || provider.getAddressLine1().equals("-")) {
			writeToFile(provider);
			throw new IllegalArgumentException("Provider with address " + provider.getAddressLine1() + "has invalid data");
		}
		return false;
	}

	private boolean isInvalidZipcode(Provider provider) throws IllegalArgumentException {
		if (provider.getZipCode().isEmpty() || provider.getZipCode().equals("-")) {
			writeToFile(provider);
			throw new IllegalArgumentException("Provider with zipcode " + provider.getZipCode() + "has invalid data");
		}
		return false;
	}

	private void writeToFile(Provider provider) {
		try {
	        // create FileWriter object with file as parameter
			FileWriter outputfile = new FileWriter(file.getAbsolutePath().concat(provider.getProviderId().toString()).concat(".csv"));
			CSVWriter writer = new CSVWriter(outputfile);
	  
	        // create a List which contains String array
	        //List<String[]> data = new ArrayList<String[]>();
	        //data.add(new String[] { provider.getProviderId().toString(), updatedProvider.getErrorDescription()});
	        writer.writeNext(new String[] { provider.getProviderId().toString(), provider.getErrorDescription()});
	  
	        // closing writer connection
	        writer.close();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}
}
