package beeriprint.newsletter.batch.mailer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author hilel
 */
public class TestJob extends TestCase {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TestJob.class);
    //org.springframework.batch.core.launch.support.CommandLineJobRunner runner;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TestJob(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(TestJob.class);
    }

    @Override
    protected void setUp() throws Exception {
    }

    public void testLetter() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/batch/jobs/clickb-upgrade.xml");
        JobLauncher launcher = context.getBean(JobLauncher.class);
        Job job = context.getBean("clickb-upgrade", Job.class);
        LOGGER.info("Adding parameters to job " + job.getName());
        JobParametersBuilder builder = new JobParametersBuilder();
        //builder.addString("list_file", "file:///var/opt/newsletter/testim/test-recipients.csv", true);
        builder.addString("list_file", "classpath:data/clickb-test-recipients.csv", true);
        builder.addString("campaign_date", "2016-07-18", true);
        builder.addString("template_name", "clickb-upgrade", false);
        JobExecution jobExecution = launcher.run(job, builder.toJobParameters());
        assertNotSame(BatchStatus.FAILED, jobExecution.getStatus());
    }

    public void _testNewsletter() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/batch/jobs/beeriprint-newsletter.xml");
        JobLauncher launcher = context.getBean(JobLauncher.class);
        Job job = context.getBean("beeriprint-newsletter", Job.class);
        LOGGER.info("Adding parameters to job " + job.getName());
        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addString("list_file", "classpath:data/newsletter-test-recipients.localhost.csv", true);
        builder.addString("campaign_date", "2016-07-18", true);
        builder.addString("template_name", "beeriprint-newsletter", false);
        JobExecution jobExecution = launcher.run(job, builder.toJobParameters());
        assertNotSame(BatchStatus.FAILED, jobExecution.getStatus());
    }
}
