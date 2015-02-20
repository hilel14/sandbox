/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mycompany.spring.batch.mail.demo.batch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.mail.SimpleMailMessageItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableBatchProcessing
public class SampleBatchApplication {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job1(@Qualifier("step1") Step step1) throws Exception {
        return this.jobBuilderFactory.get("job1").start(step1).build();
    }

    @Bean
    public Step step1(
            ItemReader<String> reader,
            ItemProcessor<String, SimpleMailMessage> processor,
            ItemWriter<SimpleMailMessage> writer) {
        return stepBuilderFactory.get("step1").<String, SimpleMailMessage>chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public ListItemReader<String> reader() {
        List<String> items = new ArrayList<>();
        items.add("111");
        items.add("222");
        items.add("333");
        ListItemReader<String> reader = new ListItemReader<>(items);
        return reader;
    }

    @Bean
    public ItemProcessor processor() {
        ItemProcessor processor = new ItemProcessor() {

            @Override
            public SimpleMailMessage process(Object item) throws Exception {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("root@localhost.localdomain");
                message.setTo("c7@localhost.localdomain");
                message.setSubject(item.toString());
                message.setSentDate(new Date());
                message.setText("Hello " + item.toString());
                System.out.println("Sending message with subject: " + item);
                return message;
            }
        };
        return processor;
    }

    @Bean
    public SimpleMailMessageItemWriter writer() {
        SimpleMailMessageItemWriter writer = new SimpleMailMessageItemWriter();
        writer.setMailSender(new JavaMailSenderImpl());
        return writer;
    }

    public static void main(String[] args) throws Exception {
        // System.exit is common for Batch applications since the exit code can be used to drive a workflow
        System.exit(SpringApplication.exit(SpringApplication.run(SampleBatchApplication.class, args)));
    }
}
