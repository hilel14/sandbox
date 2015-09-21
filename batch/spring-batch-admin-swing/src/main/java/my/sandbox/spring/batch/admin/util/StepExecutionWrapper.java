/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.sandbox.spring.batch.admin.util;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.explore.JobExplorer;

/**
 *
 * @author hilel-deb
 */
public class StepExecutionWrapper {

    private final StepExecution stepExecution;
    private final JobExplorer jobExplorer;

    public StepExecutionWrapper(StepExecution stepExecution, JobExplorer jobExplorer) {
        this.stepExecution = stepExecution;
        this.jobExplorer = jobExplorer;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(stepExecution.getStepName());
        b.append(" | ");
        b.append("read count = ").append(stepExecution.getReadCount());
        b.append(" | ");
        b.append("write count = ").append(stepExecution.getWriteCount());
        return b.toString();
    }

    /**
     * @return the stepExecution
     */
    public StepExecution getStepExecution() {
        return stepExecution;
    }

    /**
     * @return the jobExplorer
     */
    public JobExplorer getJobExplorer() {
        return jobExplorer;
    }
}
