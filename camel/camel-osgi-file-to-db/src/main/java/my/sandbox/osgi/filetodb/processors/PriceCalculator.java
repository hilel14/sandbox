/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.sandbox.osgi.filetodb.processors;

import my.sandbox.osgi.filetodb.model.Product;
import org.apache.camel.Exchange;

/**
 *
 * @author hilel-deb
 */
public class PriceCalculator {

    public void process(Exchange exchange) throws Exception {
        Product product = exchange.getIn().getBody(Product.class);
        //System.out.println(product.getDescription());
        exchange.getIn().setHeader("description", product.getDescription());
        product.setPrice(product.getPrice() + 1.00);
    }

}
