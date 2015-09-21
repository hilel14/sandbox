/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.sandbox.spring.batch.admin.util;

import java.util.List;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.explore.JobExplorer;

/**
 *
 * @author hilel-deb
 */
public class JobInstanceWrapper {

    private final JobInstance jobInstance;
    private final JobExplorer jobExplorer;

    public JobInstanceWrapper(JobInstance jobInstance, JobExplorer jobExplorer) {
        this.jobInstance = jobInstance;
        this.jobExplorer = jobExplorer;
    }

    @Override
    public String toString() {
        if (jobInstance != null) {
            StringBuilder b = new StringBuilder();
            b.append(jobInstance.getId());
            List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobInstance);
            if (jobExecutions != null) {
                JobExecution firstExecution = jobExecutions.get(0);
                JobParameters jobParameters = firstExecution.getJobParameters();
                if (jobParameters != null) {
                    b.append(" | ");
                    for (String key : jobParameters.getParameters().keySet()) {
                        b.append(key).append(" = ").append(jobParameters.getString(key));
                        b.append(" | ");
                    }
                }
            }
            return b.toString();
        }
        return null;
    }

    /**
     * @return the jobInstance
     */
    public JobInstance getJobInstance() {
        return jobInstance;
    }

    /**
     * @return the jobExplorer
     */
    public JobExplorer getJobExplorer() {
        return jobExplorer;
    }
}
