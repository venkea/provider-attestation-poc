package com.hcsc.provider.attestation.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.hcsc.provider.attestation.model.Provider;

@Configuration
@EnableBatchProcessing
public class ProviderAttestationConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	private static final String QUERY_INSERT_STUDENT = "INSERT " +
            "INTO provider(prov_id, prov_first_nm, prov_last_name) " +
            "VALUES (:provID, :provFirstNm, :provLastNm)";
	@Bean
    public ItemReader<Provider> itemReader() {
        LineMapper<Provider> studentLineMapper = createStudentLineMapper();
 
        return new FlatFileItemReaderBuilder<Provider>()
                .name("providerReader")
                .resource(new ClassPathResource("data/sample_data.txt"))
                .linesToSkip(1)
                .lineMapper(studentLineMapper)
                .build();
    }

	private LineMapper<Provider> createStudentLineMapper() {
        DefaultLineMapper<Provider> providerLineMapper = new DefaultLineMapper<>();
 
        LineTokenizer providerLineTokenizer = createProviderLineTokenizer();
        providerLineMapper.setLineTokenizer(providerLineTokenizer);
 
        FieldSetMapper<Provider> providerInformationMapper =
                createProviderInformationMapper();
        providerLineMapper.setFieldSetMapper(providerInformationMapper);
 
        return providerLineMapper;
    }
 
    private LineTokenizer createProviderLineTokenizer() {
        DelimitedLineTokenizer studentLineTokenizer = new DelimitedLineTokenizer();
        studentLineTokenizer.setDelimiter(";");
        studentLineTokenizer.setNames(new String[]{
                "provID", 
                "provFirstNm", 
                "provLastNm"
        });
        return studentLineTokenizer;
    }
 
    private FieldSetMapper<Provider> createProviderInformationMapper() {
        BeanWrapperFieldSetMapper<Provider> studentInformationMapper =
                new BeanWrapperFieldSetMapper<>();
        studentInformationMapper.setTargetType(Provider.class);
        return studentInformationMapper;
    }
    
//    @Bean
//    ItemProcessor<Provider, Provider> providerItemProcessor() {
//        return new LoggingStudentProcessor();
//    }

    @Bean
    ItemWriter<Provider> providerItemWriter(DataSource dataSource, NamedParameterJdbcTemplate jdbcTemplate) {
        JdbcBatchItemWriter<Provider> databaseItemWriter = new JdbcBatchItemWriter<>();
        databaseItemWriter.setDataSource(dataSource);
        databaseItemWriter.setJdbcTemplate(jdbcTemplate);

        databaseItemWriter.setSql(QUERY_INSERT_STUDENT);

        ItemSqlParameterSourceProvider<Provider> sqlParameterSourceProvider = studentSqlParameterSourceProvider();
        databaseItemWriter.setItemSqlParameterSourceProvider(sqlParameterSourceProvider);

        return databaseItemWriter;
    }
   
    private ItemSqlParameterSourceProvider<Provider> studentSqlParameterSourceProvider() {
        return new BeanPropertyItemSqlParameterSourceProvider<>();
    }

    @Bean
    Step providerFileToSaveStep(ItemReader<Provider> itemReader,
                               //ItemProcessor<Provider, Provider> providerItemProcessor,
                               ItemWriter<Provider> csvFileDatabaseItemWriter,
                               StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("providerFileToSaveStep")
                .<Provider, Provider>chunk(1)
                .reader(itemReader)
                .writer(csvFileDatabaseItemWriter)
                .build();
    }

    @Bean
    Job providerFileToSaveJob(JobCompletionNotificationListner listener, JobBuilderFactory jobBuilderFactory,
                             @Qualifier("providerFileToSaveStep") Step providerStep) {
        return jobBuilderFactory.get("providerFileToSaveJob")
                .incrementer(new RunIdIncrementer())
                .flow(providerStep)
                .end()
                .build();
    }
}
