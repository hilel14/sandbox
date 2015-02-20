/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.my.spring.batch.java.config.demo.configuration;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 *
 * @author hilel
 */
@Configuration
@PropertySource("classpath:mysql.job1.properties")
public class JobSetup {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource job1DataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("job1.jdbc.driver"));
        dataSource.setUrl(environment.getProperty("job1.jdbc.url"));
        dataSource.setUsername(environment.getProperty("job1.jdbc.user"));
        dataSource.setPassword(environment.getProperty("job1.jdbc.password"));
        return dataSource;
    }

}
