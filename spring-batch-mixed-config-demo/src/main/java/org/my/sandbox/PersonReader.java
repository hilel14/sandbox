/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.my.sandbox;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

/**
 *
 * @author hilel
 */
@Component("reader")
@Scope(value = "step", proxyMode = ScopedProxyMode.NO)
//@StepScope
public class PersonReader extends FlatFileItemReader {

    @Autowired
    public void setInputFile(@Value("#{jobParameters[inputFile]}") String inputFile) {
        setResource(new FileSystemResource(inputFile));
    }

    public PersonReader() {

        DefaultLineMapper<Person> defaultLineMapper = new DefaultLineMapper<>();

        defaultLineMapper.setLineTokenizer(new DelimitedLineTokenizer() {
            {
                setNames(new String[]{"firstName", "lastName"});
            }
        });

        defaultLineMapper.setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {
            {
                setTargetType(Person.class);
            }
        });

        setLineMapper(defaultLineMapper);
        //setResource(new ClassPathResource("sample-data.csv"));
    }
}
