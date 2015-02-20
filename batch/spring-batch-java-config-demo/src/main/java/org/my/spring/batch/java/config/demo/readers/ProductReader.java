/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.my.spring.batch.java.config.demo.readers;

import org.my.spring.batch.java.config.demo.model.Product;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

/**
 *
 * @author hilel
 */
@Component("reader1")
@StepScope
public class ProductReader extends FlatFileItemReader {

    @Autowired
    public ProductReader(@Value("#{jobParameters[inputFile]}") String inputFile) {

        setResource(new FileSystemResource(inputFile));

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[]{"description", "price", "purchaseDate"});
        tokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_TAB);

        BeanWrapperFieldSetMapper<Product> mapper = new BeanWrapperFieldSetMapper<>();
        mapper.setTargetType(Product.class);

        DefaultLineMapper<Product> defaultLineMapper = new DefaultLineMapper<>();

        defaultLineMapper.setLineTokenizer(tokenizer);
        defaultLineMapper.setFieldSetMapper(mapper);

        setLineMapper(defaultLineMapper);
    }
}
