package org.ashqelon.spring.batch.job.operator;

import java.util.List;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App {

    private JobExplorer jobExplorer;
    private JobOperator jobOperator;

    public static void main(String[] args) throws Exception {
        //org.springframework.batch.core.launch.support.CommandLineJobRunner.main(args);
        App app = new App();
        app.init();
        //app.printJobsInfo();
        app.printSummary(1);
    }

    /**
     * Injection setter for {@link JobExplorer}.
     *
     * @param jobExplorer the {@link JobExplorer} to set
     */
    public void setJobExplorer(JobExplorer jobExplorer) {
        this.jobExplorer = jobExplorer;
    }

    /**
     * Injection setter for {@link JobOperator}.
     *
     * @param jobOperator the {@link JobOperator} to set
     */
    public void setJobOperator(JobOperator jobOperator) {
        this.jobOperator = jobOperator;
    }

    public void init() {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("launch-context.xml");
        context.getAutowireCapableBeanFactory().autowireBeanProperties(this, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);
    }

    private void printJobsInfo() throws NoSuchJobExecutionException, JobExecutionNotRunningException {
        System.out.println("Total jobs: " + jobExplorer.getJobNames().size());
        for (String job : jobExplorer.getJobNames()) {
            System.out.println(job);
            List<JobInstance> jobInstances = jobExplorer.getJobInstances(job, 0, 999);
            for (JobInstance instance : jobInstances) {
                System.out.println("  " + instance.toString());
                List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(instance);
                for (JobExecution jobExecution : jobExecutions) {
                    System.out.println("    " + jobExecution.toString());
                    if (jobExecution.isRunning()) {
                        System.out.println("      job execution " + jobExecution.getId() + " is running");
                        jobOperator.stop(jobExecution.getId());
                    }
                }
            }
        }
    }

    private void printSummary(long executionId) throws NoSuchJobException, NoSuchJobExecutionException {
        JobExecution jobExecution = jobExplorer.getJobExecution(executionId);
        System.out.println(jobExecution);
        System.out.println("job name: " + jobExecution.getJobInstance().getJobName());
        System.out.println("job parameters: " + jobExecution.getJobParameters());
        System.out.println("cycle date: " + jobExecution.getJobParameters().getString("cycle.date"));
        System.out.println("star time: " + jobExecution.getStartTime());
        System.out.println("status: " + jobExecution.getStatus());
    }
}
