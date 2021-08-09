package com.hcsc.provider.attestation.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hcsc.provider.attestation.listener.StepSkipListener;
import com.hcsc.provider.attestation.model.Provider;
import com.hcsc.provider.attestation.processor.ProviderAttestationProcessor;
import com.hcsc.provider.attestation.writer.ConsoleItemWriter;

@Configuration
public class ProviderAttestationConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	public JobCompletionNotificationListner jobCompletionNotificationListner;

	private static final String QUERY_INSERT_STUDENT = "INSERT " +
            "INTO provider(prov_id, prov_first_nm, prov_last_name) " +
            "VALUES (:providerId, :providerFirstName, :providerLastName)";
	
	@Bean
    public FlatFileItemReader<Provider> reader() {
        FlatFileItemReader<Provider> itemReader = new FlatFileItemReader<Provider>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(new ClassPathResource("data/Delimited_Source2.txt"));
        return itemReader;
    }

	@Bean
    public LineMapper<Provider> lineMapper() {
        DefaultLineMapper<Provider> lineMapper = new DefaultLineMapper<Provider>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(";");
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
                "provEffectiveStatus"});
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
    public Job readProviderDataJob() {
        return jobBuilderFactory
                .get("readProviderDataJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .listener(jobCompletionNotificationListner)
                .build();
    }
 
    @Bean
    public Step step1() {
        return stepBuilderFactory
                .get("step1")
                .<Provider, Provider>chunk(10)
                .reader(reader())
                .processor(processor())
                .faultTolerant()
                .skip(IllegalArgumentException.class)
                .skipLimit(10)
                .listener(new StepSkipListener())
                .writer(writer())
                .build();
    }

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
