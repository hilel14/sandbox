/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.my.spring.batch.java.config.demo.processors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

/**
 *
 * @author hilel
 */
@Component("tasklet1")
public class SimpleTasklet implements Tasklet {

    private static final Log logger = LogFactory.getLog(SimpleTasklet.class);

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        logger.info("taslket one");
        return null;
    }

}
