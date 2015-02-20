/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.my.spring.batch.java.config.demo.writers;

import javax.sql.DataSource;
import org.my.spring.batch.java.config.demo.model.Product;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author hilel
 */
@Component("writer1")
//@StepScope
public class ProductWriter extends JdbcBatchItemWriter<Product> {

    @Autowired
    public ProductWriter(@Qualifier("job1DataSource") DataSource dataSource) {
        setDataSource(dataSource);
        setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Product>());
        setSql("INSERT INTO product (description, price, purchase_date) VALUES (:description, :price, :purchaseDate)");
    }
}
