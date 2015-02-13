/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.camel.sandbox.filedemo.processors;

import my.camel.sandbox.filedemo.model.Product;
import org.apache.camel.Exchange;

/**
 *
 * @author c7
 */
public class QueryBuilder {

    public void process(Exchange exchange) throws Exception {
        Product product = exchange.getIn().getBody(Product.class);
        String query = String.format("INSERT INTO product "
                + "(description, price, purchase_date) "
                + "VALUES ('%s', %s, '%s')",
                product.getDescription(), product.getPrice(), product.getPurchaseDateFormatted());
        exchange.getIn().setHeader("insert.query", query);
    }
}
