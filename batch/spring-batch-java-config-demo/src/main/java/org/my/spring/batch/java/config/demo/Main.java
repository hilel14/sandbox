/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.my.spring.batch.java.config.demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author hilel
 */
public class Main {

    static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        //System.exit(SpringApplication.exit(SpringApplication.run(MyBatchConfiguration.class, args)));
        logger.log(Level.INFO, "extracting  job parameters from command line arguments {0}", args);
        JobParameters jobParameters = parseJobParameters(args);
        String basePackage = Main.class.getPackage().getName();
        logger.log(Level.INFO, "scanning {0} for classes with bean definitions", basePackage);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(basePackage);
        Job job1 = context.getBean("job1", Job.class);
        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        try {
            JobExecution jobExecution = jobLauncher.run(job1, jobParameters);
            logger.log(Level.INFO, "job execution {0} ended with status: {1}", new Object[]{jobExecution.getId(), jobExecution.getStatus()});
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    private static JobParameters parseJobParameters(String[] args) {
        Map<String, JobParameter> parameters = new HashMap<>();
        parameters.put("inputFile", new JobParameter("/var/opt/data/sample-data-2.csv"));
        parameters.put("k1", new JobParameter(new Date()));
        return new JobParameters(parameters);
    }
}
