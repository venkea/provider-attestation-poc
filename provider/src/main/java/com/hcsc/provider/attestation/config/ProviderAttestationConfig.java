package com.hcsc.provider.attestation.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hcsc.provider.attestation.listener.StepSkipListener;
import com.hcsc.provider.attestation.model.Provider;
import com.hcsc.provider.attestation.processor.ProviderAttestationProcessor;
import com.hcsc.provider.attestation.writer.ConsoleItemWriter;
import com.opencsv.CSVWriter;

@Configuration
public class ProviderAttestationConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	public DataSource dataSource;
	
	@Autowired
	public JobCompletionNotificationListner jobCompletionNotificationListner;

	private static final String QUERY_INSERT_PROVIDER = "INSERT " +
            "INTO provider(provider_id,provider_type, provider_first_name, provider_last_name,tax_id,"
            + "npi,ssn,licence_expiry_date,address_line_1,state,zip_code,provider_specialty_code,network_requested, site_id, comp_code, service_county ) " +
            "VALUES (:providerId,:provType, :providerFirstName, :providerLastName,:taxId,"
            + ":npi,:ssn,STR_TO_DATE(:licenseExpiryDate, '%d/%m/%Y'),:addressLine1,:state,:zipCode,:providerSpecialityCode,"
            + ":networkRequested, :siteId, :compCode, :serviceCounty)";
	
	private static final String QUERY_INSERT_PROVIDER_DETAILS = "INSERT INTO provider_detail"
			+ "(provider_id,dea_id, ein, licence_state,licence_number,"
            + "address_type,address_line_2,city,region,phone_number,email_id,provider_practice_state,product,network_requested, site_id, comp_code, service_county) " +
            "VALUES (:providerId,:deaId, :ein, :licenceState,:licenceNumber,"
            + ":addressType,:addressLine2,:city,:region,:phoneNumber,:emailId,:providerPracticeState,:product,"
            + ":networkRequested, :siteId, :compCode, :serviceCounty)";
	
	@Bean
    public FlatFileItemReader<Provider> reader() throws IOException {
        FlatFileItemReader<Provider> itemReader = new FlatFileItemReader<Provider>();
        itemReader.setResource(new ClassPathResource("data/Delimited_Source1.txt"));
        itemReader.setLineMapper(lineMapper());
        itemReader.setLinesToSkip(1);
        return itemReader;
    }

	@Bean
    public LineMapper<Provider> lineMapper() throws IOException {
        DefaultLineMapper<Provider> lineMapper = new DefaultLineMapper<Provider>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        
        String line = "";
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/Delimited_Source1.txt"));
        	while ((line = reader.readLine()) != null) {
                // if pipe as separator
                if (line.contains("|")) {
                    lineTokenizer.setDelimiter("|");
                // if semi-colon as separator
                } else if (line.contains(";")) {
                    lineTokenizer.setDelimiter(";");
                }
            }

        lineTokenizer.setNames(new String[] { "providerId", 
                "providerFirstName",
                "providerLastName",
                "addressType",
                "addressLine1",
                "addressLine2",
                "city",
                "state",
                "region",
                "zipCode",
                "phoneNumber",
                "emailId",
                "npi",
                "taxId",
                "deaId",
                "ssn",
                "ein",
                "providerSpecialityCode",
                "licenceState",
                "licenceNumber",
                "licenseExpiryDate",
                "providerPracticeState",
                "product",
                "provType",
                "provEffectiveDate",
                "provEffectiveStatus",
                "networkRequested",
                "siteId",
                "compCode",
                "serviceCounty"});
        BeanWrapperFieldSetMapper<Provider> fieldSetMapper = new BeanWrapperFieldSetMapper<Provider>();
        fieldSetMapper.setTargetType(Provider.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
    
    @Bean
    ItemProcessor<Provider, Provider> processor() {
        return new ProviderAttestationProcessor();
    }
    
    @Bean
    public ConsoleItemWriter<Provider> writer() {
        return new ConsoleItemWriter<Provider>();
    }
    

    @Bean
    public JdbcBatchItemWriter<Provider> writerToProvider() {
    	
    	JdbcBatchItemWriter<Provider> itemWriter = new JdbcBatchItemWriter<>();
		itemWriter.setDataSource(this.dataSource);
		itemWriter.setSql(QUERY_INSERT_PROVIDER);
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
		itemWriter.afterPropertiesSet();
		
        return itemWriter;
    }

    @Bean
    public JdbcBatchItemWriter<Provider> writerToProviderDetails() {
    	
    	JdbcBatchItemWriter<Provider> itemWriter = new JdbcBatchItemWriter<>();

		itemWriter.setDataSource(this.dataSource);
		itemWriter.setSql(QUERY_INSERT_PROVIDER_DETAILS);
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
		itemWriter.afterPropertiesSet();
		
        return itemWriter;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
    public CompositeItemWriter compositeWriter() throws Exception {
        CompositeItemWriter compositeItemWriter = new CompositeItemWriter();
        List<ItemWriter> writers = new ArrayList<ItemWriter>();
        writers.add(writerToProvider());
        writers.add(writerToProviderDetails());
        compositeItemWriter.setDelegates(writers);
        return compositeItemWriter;
    }

    @Bean
    public Job readProviderDataJob() throws Exception {
        return jobBuilderFactory
                .get("readProviderDataJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .listener(jobCompletionNotificationListner)
                .build();
    }
 
    @Bean
    public Step step1() throws Exception {
        return stepBuilderFactory
                .get("step1")
                .<Provider, Provider>chunk(10)
                .reader(reader())
                .processor(processor())
                .faultTolerant()
                .skip(IllegalArgumentException.class)
                .skipLimit(10)
                .listener(new StepSkipListener())
                .writer(compositeWriter())
                .build();
    }

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
