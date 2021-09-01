package com.hcsc.provider.attestation.config;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.hcsc.provider.attestation.model.ProviderLogs;

@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {
	@Value("${spring.datasource.driver-class-name}")
    private String DRIVER;

	@Value("${spring.datasource.password}")
    private String PASSWORD;

	@Value("${spring.datasource.url}")
    private String URL;

	@Value("${spring.datasource.username}")
    private String USERNAME;

	@Value("${spring.jpa.properties.hibernate.dialect}")
    private String DIALECT;

	@Value("${spring.jpa.show-sql}")
    private String SHOW_SQL;

	@Value("${spring.jpa.hibernate.ddl-auto}")
    private String HBM2DDL_AUTO;

//	@Value("${entitymanager.packagesToScan}")
//    private String PACKAGES_TO_SCAN;


    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setAnnotatedClasses(ProviderLogs.class);
        //sessionFactory.setPackagesToScan(this.PACKAGES_TO_SCAN);
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", this.DIALECT);
        hibernateProperties.put("hibernate.show_sql", this.SHOW_SQL);
        hibernateProperties.put("hibernate.hbm2ddl.auto", this.HBM2DDL_AUTO);
        sessionFactory.setHibernateProperties(hibernateProperties);

        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }   
}
