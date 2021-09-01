package com.hcsc.provider.attestation.writer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hcsc.provider.attestation.common.CommonConstants;
import com.hcsc.provider.attestation.model.Provider;

public class ProviderWriter implements ItemWriter<Provider> {

	private JdbcTemplate jdbcTemplate;

	public ProviderWriter(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void write(List<? extends Provider> items) throws Exception {
		// TODO Auto-generated method stub
		for(Provider provider: items) {
			Date date = new SimpleDateFormat("MM/dd/yyyy").parse(provider.getLicenseExpiryDate());		
			if (!provider.getStatus().equals("FAILED")) {
				jdbcTemplate.update("INSERT INTO provider(provider_id,provider_type, provider_first_name, "
						+ "provider_last_name,tax_id,"
			            + "npi,ssn,licence_expiry_date,address_line_1,state,zip_code,provider_specialty_code,"
			            + "network_requested, site_id, comp_code, service_county ) " +
			            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", provider.getProviderId(), 
			            provider.getProvType(), provider.getProviderFirstName(), provider.getProviderLastName(),
			            provider.getTaxId(), provider.getNpi(), provider.getSsn(), date,
			            provider.getAddressLine1(), provider.getState(), provider.getZipCode(), provider.getProviderSpecialityCode(),
			            provider.getNetworkRequested(), provider.getSiteId(), provider.getCompCode(), provider.getServiceCounty());
				jdbcTemplate.update("INSERT INTO provider_detail"
						+ "(provider_id,dea_id, ein, licence_state,licence_number,"
			            + "address_type,address_line_2,city,region,phone_number,email_id,"
			            + "provider_practice_state,product,network_requested, site_id, comp_code, service_county) " +
			            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", provider.getProviderId(), 
			            provider.getDeaId(), provider.getEin(), provider.getLicenceState(),
			            provider.getLicenceNumber(), provider.getAddressType(), provider.getAddressLine2(), 
			            provider.getCity(),provider.getRegion(), provider.getPhoneNumber(), provider.getEmailId(),
			            provider.getProviderPracticeState(), provider.getProduct(),
			            provider.getNetworkRequested(), provider.getSiteId(), provider.getCompCode(),
			            provider.getServiceCounty());
			} else {
				SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
				String dateString = sdf.format(new Date());
				Date errorDate = sdf.parse(dateString);
				
				jdbcTemplate.update("INSERT INTO provider_logs(job_execution_id, provider_id, date, log_description" +
						") VALUES(?, ?, ?, ?)" , CommonConstants.jobExecutionId, provider.getProviderId(),
						errorDate, provider.getErrorDescription());
			}
		}
		
	}


}
