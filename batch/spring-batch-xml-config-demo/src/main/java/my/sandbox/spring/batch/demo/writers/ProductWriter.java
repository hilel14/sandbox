/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.sandbox.spring.batch.demo.writers;

import javax.sql.DataSource;
import my.sandbox.spring.batch.demo.model.Product;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

/**
 *
 * @author hilel
 */
public class ProductWriter extends JdbcBatchItemWriter<Product> {

    public void setJob1DataSource(final DataSource dataSource) {
        setDataSource(dataSource);
    }

    public ProductWriter() {
        setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Product>());
        setSql("INSERT INTO product (description, price, purchase_date) VALUES (:description, :price, :purchaseDate)");
    }
}
