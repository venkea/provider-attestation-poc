package com.hcsc.provider.attestation.controller;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
public class JobInvokerController {
 
    @Autowired
    JobLauncher jobLauncher;
 
    @Autowired
    Job processJob;
 
    @RequestMapping("/invokejob")
    public String handle() throws Exception {
 
//            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
//                    .toJobParameters();
//            jobLauncher.run(processJob, jobParameters);
            
    	JobParametersBuilder builder = new JobParametersBuilder();
    	builder.addDate("date", new Date());
    	jobLauncher.run(processJob, builder.toJobParameters());
    	
        return "Batch job has been invoked";
    }
}
