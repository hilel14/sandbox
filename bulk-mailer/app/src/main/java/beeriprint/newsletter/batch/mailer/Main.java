package beeriprint.newsletter.batch.mailer;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.LoggerFactory;
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
public class Main {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Mandatory arguments: job-name input-file");
            System.out.println("Exmaple: beeriprint-newsletter /var/opt/newsletter/2016-04-01/list-1.csv");
            System.exit(1);
        }
        // parse user input
        String jobName = args[0];
        Path inFile = Paths.get(args[1]);
        String campaignDate = inFile.getParent().getFileName().toString();
        // configure the job
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/batch/jobs/" + jobName + ".xml");
        JobLauncher launcher = context.getBean(JobLauncher.class);
        Job job = context.getBean(jobName, Job.class);
        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addString("template_name", jobName, true);
        builder.addString("list_file", "file://" + inFile.toAbsolutePath().toString(), true);
        builder.addString("campaign_date", campaignDate, true);
        // run the job
        try {
            JobExecution jobExecution = launcher.run(job, builder.toJobParameters());
        } catch (Exception ex) {
            LOGGER.error(null, ex);
        }
    }
}
