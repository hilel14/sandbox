package my.sandbox.spring.batch.demo.processors;

import my.sandbox.spring.batch.demo.model.Product;
import org.springframework.batch.item.ItemProcessor;

public class ProductProcessor implements ItemProcessor<Product, Product> {

    @Override
    public Product process(final Product product) throws Exception {
        product.setDescription(product.getDescription().toUpperCase());
        return product;
    }

}
