/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.spring.sandbox.spring.batch.demo.readers;

import my.spring.sandbox.spring.batch.demo.model.Person;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;

/**
 *
 * @author hilel
 */
public class PersonReader extends FlatFileItemReader {

    public void setInputFile(String inputFile) {
        setResource(new FileSystemResource(inputFile));
    }

    public PersonReader() {

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[]{"firstName", "lastName"});
        //tokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_TAB);

        BeanWrapperFieldSetMapper<Person> mapper = new BeanWrapperFieldSetMapper<>();
        mapper.setTargetType(Person.class);

        DefaultLineMapper<Person> defaultLineMapper = new DefaultLineMapper<>();

        defaultLineMapper.setLineTokenizer(tokenizer);
        defaultLineMapper.setFieldSetMapper(mapper);

        setLineMapper(defaultLineMapper);
    }
}
