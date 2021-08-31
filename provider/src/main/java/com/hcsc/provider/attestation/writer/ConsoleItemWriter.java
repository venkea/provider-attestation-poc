package com.hcsc.provider.attestation.writer;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.hcsc.provider.attestation.model.Provider;

public class ConsoleItemWriter<T> implements ItemWriter<T>{ 

	private static final Logger LOG = LoggerFactory.getLogger(ConsoleItemWriter.class);

	@Autowired
	public DataSource dataSource;
	
	public static String QUERY_INSERT_PROVIDER;
	
	public static String QUERY_INSERT_PROVIDER_DETAILS;

//	private static final String QUERY_INSERT_PROVIDER = "INSERT " +
//            "INTO provider(provider_id,provider_type, provider_first_name, provider_last_name,tax_id,"
//            + "npi,ssn,licence_expiry_date,address_line_1,state,zip_code,provider_specialty_code,network_requested, site_id, comp_code, service_county ) " +
//            "VALUES (:providerId,:provType, :providerFirstName, :providerLastName,:taxId,"
//            + ":npi,:ssn,STR_TO_DATE(:licenseExpiryDate, '%d/%m/%Y'),:addressLine1,:state,:zipCode,:providerSpecialityCode,"
//            + ":networkRequested, :siteId, :compCode, " + :serviceCounty +")";
//	
//	private static final String QUERY_INSERT_PROVIDER_DETAILS = "INSERT INTO provider_detail"
//			+ "(provider_id,dea_id, ein, licence_state,licence_number,"
//            + "address_type,address_line_2,city,region,phone_number,email_id,provider_practice_state,product,network_requested, site_id, comp_code, service_county) " +
//            "VALUES (:providerId,:deaId, :ein, :licenceState,:licenceNumber,"
//            + ":addressType,:addressLine2,:city,:region,:phoneNumber,:emailId,:providerPracticeState,:product,"
//            + ":networkRequested, :siteId, :compCode, :serviceCounty)";
	
//	public void write(List<Provider> providers) throws Exception {
//		LOG.info("Console item writer starts");
//		LOG.info("Total Providers to Write " + providers.size());
//		for (Provider provider : providers) {
//			LOG.info("Writing  " + provider); 
//        } 
//		 LOG.info("Console item writer ends");
//	} 
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public CompositeItemWriter compositeWriter() throws Exception {
        CompositeItemWriter compositeItemWriter = new CompositeItemWriter();
        List<ItemWriter> writers = new ArrayList<ItemWriter>();
        writers.add(writerToProvider());
        writers.add(writerToProviderDetails());
        compositeItemWriter.setDelegates(writers);
        return compositeItemWriter;
    }

    public JdbcBatchItemWriter<Provider> writerToProvider() {
    	
    	JdbcBatchItemWriter<Provider> itemWriter = new JdbcBatchItemWriter<>();
		itemWriter.setDataSource(this.dataSource);
		itemWriter.setSql(QUERY_INSERT_PROVIDER);
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
		itemWriter.afterPropertiesSet();
		
        return itemWriter;
    }

    public JdbcBatchItemWriter<Provider> writerToProviderDetails() {
    	
    	JdbcBatchItemWriter<Provider> itemWriter = new JdbcBatchItemWriter<>();

		itemWriter.setDataSource(this.dataSource);
		itemWriter.setSql(QUERY_INSERT_PROVIDER_DETAILS);
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
		itemWriter.afterPropertiesSet();
		
        return itemWriter;
    }

	@Override
	public void write(List<? extends T> providers) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("writer length ---" +providers.size());
		for (T provider : providers) {
			
			Provider updatedProvider = (Provider)provider;
			QUERY_INSERT_PROVIDER = "INSERT " +
		            "INTO provider(provider_id,provider_type, provider_first_name, provider_last_name,tax_id,"
		            + "npi,ssn,licence_expiry_date,address_line_1,state,zip_code,provider_specialty_code,network_requested, site_id, comp_code, service_county ) " +
		            "VALUES (:providerId,:provType, :providerFirstName, :providerLastName,:taxId,"
		            + ":npi,:ssn,STR_TO_DATE(:licenseExpiryDate, '%d/%m/%Y'),:addressLine1,:state,:zipCode,:providerSpecialityCode,"
		            + ":networkRequested, :siteId, :compCode, " + updatedProvider.getServiceCounty() +")";
			
			QUERY_INSERT_PROVIDER_DETAILS = "INSERT INTO provider_detail"
					+ "(provider_id,dea_id, ein, licence_state,licence_number,"
		            + "address_type,address_line_2,city,region,phone_number,email_id,provider_practice_state,product,network_requested, site_id, comp_code, service_county) " +
		            "VALUES (:providerId,:deaId, :ein, :licenceState,:licenceNumber,"
		            + ":addressType,:addressLine2,:city,:region,:phoneNumber,:emailId,:providerPracticeState,:product,"
		            + ":networkRequested, :siteId, :compCode, " + updatedProvider.getServiceCounty() +")";
			writerToProviderDetails();
			System.out.println("Updated provider service county --- "  + updatedProvider.getProviderId()  + "  "
				+updatedProvider.getServiceCounty());
		}
	}
}
