/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.sandbox.spring.batch.demo.readers;

import my.sandbox.spring.batch.demo.model.Product;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;

/**
 *
 * @author hilel
 */
public class ProductReader extends FlatFileItemReader {

    public void setInputFile(String inputFile) {
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
