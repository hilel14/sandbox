/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.my.sandbox;

import javax.sql.DataSource;
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
public class ProductWriter extends JdbcBatchItemWriter<Product> {

    @Autowired
    public void setPeopleDataSource(@Qualifier("job1DataSource") DataSource job1DataSource) {
        setDataSource(job1DataSource);
    }

    public ProductWriter() {
        setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Product>());
        setSql("INSERT INTO product (description, price, purchase_date) VALUES (:description, :price, :purchaseDate)");
    }
}
