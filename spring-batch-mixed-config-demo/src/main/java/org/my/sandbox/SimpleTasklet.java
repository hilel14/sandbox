/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.my.sandbox;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 *
 * @author hilel
 */
@Component("tasklet1")
@PropertySource("classpath:application.properties")
public class SimpleTasklet implements Tasklet {

    private static final Log logger = LogFactory.getLog(SimpleTasklet.class);

    @Autowired
    private Environment environment;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        logger.info("Input file charset is: " + environment.getProperty("inputFileCharset"));
        return null;
    }

}
