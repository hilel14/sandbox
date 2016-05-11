/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.sandbox.spring.batch.demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static junit.framework.Assert.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author c7
 */
public class MainTest {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MainTest.class);
    //org.springframework.batch.core.launch.support.CommandLineJobRunner runner;
    ApplicationContext context;
    JobLauncher launcher;
    Job job;
    JobParameters parameters;

    public MainTest() {

        context = new ClassPathXmlApplicationContext("test-launch-context.xml");
        launcher = context.getBean(JobLauncher.class);
        job = context.getBean(Job.class);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test the job
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testMain() throws Exception {
        logger.info("running job " + job.getName());
        parameters = createJobParameters();
        JobExecution jobExecution = launcher.run(job, parameters);
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }

    private JobParameters createJobParameters() {
        // classpath:/job1.xml job1 inputFile=/var/opt/data/sample-data.csv r=1003
        Map<String, JobParameter> map = new HashMap<>();
        //map.put("run.id", new JobParameter(new Date().getTime()));
        map.put("inputFile", new JobParameter("/var/opt/data/in/sample-data.csv"));
        return new JobParameters(map);
    }

}
