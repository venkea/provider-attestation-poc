package com.hcsc.provider.attestation.util;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulingConfiguration {

    @Autowired
    JobLauncher jobLauncher;
 
    @Autowired
    Job processJob;
	
	@Scheduled(cron = "${cron.expression}")
	public void invokeJob() throws Exception {
		
		JobParametersBuilder builder = new JobParametersBuilder();
    	builder.addDate("date", new Date());
    	jobLauncher.run(processJob, builder.toJobParameters());
	}
}
