package org.my.spring.batch.java.config.demo.processors;

import org.my.spring.batch.java.config.demo.model.Product;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component("prcessor1")
public class ProductProcessor implements ItemProcessor<Product, Product> {

    @Override
    public Product process(final Product product) throws Exception {
        product.setDescription(product.getDescription().toUpperCase());
        return product;
    }

}
