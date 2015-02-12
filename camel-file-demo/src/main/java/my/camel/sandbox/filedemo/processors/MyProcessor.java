/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.camel.sandbox.filedemo.processors;

import my.camel.sandbox.filedemo.model.Product;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 *
 * @author hilel-deb
 */
public class MyProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Product product = exchange.getIn().getBody(Product.class);
        System.out.println(product.getDescription());
        exchange.getIn().setHeader("description", product.getDescription());
        product.setPrice(product.getPrice() + 1.00);
    }

}
