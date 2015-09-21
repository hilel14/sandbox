/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.sandbox.spring.batch.admin.util;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.explore.JobExplorer;

/**
 *
 * @author hilel-deb
 */
public class JobExecutionWrapper {

    private final JobExecution jobExecution;
    private final JobExplorer jobExplorer;

    public JobExecutionWrapper(JobExecution jobExecution, JobExplorer jobExplorer) {
        this.jobExecution = jobExecution;
        this.jobExplorer = jobExplorer;
    }

    @Override
    public String toString() {
        if (jobExecution != null) {
            StringBuilder b = new StringBuilder();
            // id=1, version=2, 
            // startTime=2014-03-24 13:21:03.0, endTime=2014-03-24 13:21:08.0, lastUpdated=2014-03-24 13:21:08.0, 
            // status=COMPLETED, exitStatus=exitCode=COMPLETED;exitDescription=, 
            b.append(jobExecution.getId());
            b.append(" | ");
            b.append("startTime = ").append(jobExecution.getStartTime());
            b.append(" | ");
            b.append("endTime = ").append(jobExecution.getEndTime());
            b.append(" | ");
            b.append("status = ").append(jobExecution.getStatus());
            return b.toString();
        }
        return null;
    }

    /**
     * @return the jobExecution
     */
    public JobExecution getJobExecution() {
        return jobExecution;
    }

    /**
     * @return the jobExplorer
     */
    public JobExplorer getJobExplorer() {
        return jobExplorer;
    }

    public boolean isRunning() {
        return jobExecution.isRunning();
    }
}
