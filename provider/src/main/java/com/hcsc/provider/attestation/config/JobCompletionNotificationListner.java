package com.hcsc.provider.attestation.config;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.hcsc.provider.attestation.common.CommonConstants;



@Component
public class JobCompletionNotificationListner implements JobExecutionListener {

  private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListner.class);


  @Override
	public void beforeJob(JobExecution jobExecution) {
	  CommonConstants.jobExecutionId = BigInteger.valueOf(jobExecution.getId());
	    System.out.println("--->Executing job id " + jobExecution.getId());
	}
 
  @Override
  public void afterJob(JobExecution jobExecution) {
    if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("!!! JOB FINISHED! Time to verify the results");
//      jdbcTemplate.query("SELECT * FROM provider",
//    	        (rs, row) -> new Provider(
//    	          rs.getString(1),
//    	          rs.getString(2),
//    	          rs.getString(3))
//    	      ).forEach(provider -> log.info("Found <" + provider + "> in the database."));
    }
  }
}
