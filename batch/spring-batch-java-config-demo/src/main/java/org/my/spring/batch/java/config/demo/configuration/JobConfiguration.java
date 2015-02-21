/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.my.spring.batch.java.config.demo.configuration;

import java.text.ParseException;
import org.my.spring.batch.java.config.demo.listeners.LogProcessListener;
import org.my.spring.batch.java.config.demo.model.Product;
import org.my.spring.batch.java.config.demo.processors.ProductProcessor;
import org.my.spring.batch.java.config.demo.processors.SimpleTasklet;
import org.my.spring.batch.java.config.demo.readers.ProductReader;
import org.my.spring.batch.java.config.demo.writers.ProductWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.listener.StepListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author hilel
 *
 * All bean methods get their parameters by dependency injection. Qualifier
 * annotation is used when there is more then one candidate bean.
 */
@Configuration
public class JobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job1(
            @Qualifier("step1") Step step1,
            @Qualifier("step2") Step step2) {
        return jobBuilderFactory.get("job1")
                .start(step1)
                .next(step2)
                .build();
    }

    @Bean
    public Step step1(SimpleTasklet simpleTasklet) {
        return stepBuilderFactory.get("step1").tasklet(simpleTasklet).build();
    }

    @Bean
    public Step step2(ProductReader reader, ProductProcessor processor, ProductWriter writer) {
        return stepBuilderFactory.get("step2")
                .<Product, Product>chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new LogProcessListener())
                //.faultTolerant().skipLimit(100).skip(ParseException.class)
                .build();
    }
}
