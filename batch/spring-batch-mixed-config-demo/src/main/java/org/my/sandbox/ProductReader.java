/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.my.sandbox;

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
@Component("reader1")
//@StepScope
@Scope(value = "step", proxyMode = ScopedProxyMode.NO)
public class ProductReader extends FlatFileItemReader {

    @Autowired
    public void setInputFile(@Value("#{jobParameters[inputFile]}") String inputFile) {
        setResource(new FileSystemResource(inputFile));
    }

    public ProductReader() {

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
