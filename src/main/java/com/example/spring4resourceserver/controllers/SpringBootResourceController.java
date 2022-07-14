package com.example.spring4resourceserver.controllers;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class SpringBootResourceController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job processJob;

    @Autowired
    @Qualifier("exportUserJob")
    private Job processUserJob;

    @GetMapping("/demo")
    public String demo(Principal principal) {

        return "Hello "+principal.getName()+", Auth 2.0 Resource Server, Access Granted by authentication server..";
    }

    @RequestMapping("/invokejob")
    //https://stackoverflow.com/questions/56710477/how-could-i-assign-dynamic-properties-to-a-spring-batch-configuration
    public String handle() throws Exception {

        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(processUserJob, jobParameters);

        return "Batch job has been invoked";
    }

    @RequestMapping("/invokejob2")
    //https://stackoverflow.com/questions/56710477/how-could-i-assign-dynamic-properties-to-a-spring-batch-configuration
    public String handle2() throws Exception {

        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(processUserJob, jobParameters);

        return "Batch 2 job has been invoked";
    }
}
