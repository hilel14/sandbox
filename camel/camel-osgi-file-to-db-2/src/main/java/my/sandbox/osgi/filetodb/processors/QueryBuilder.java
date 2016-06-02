package my.sandbox.osgi.filetodb.processors;

import java.util.HashMap;
import java.util.Map;
import my.sandbox.osgi.filetodb.model.Product;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 *
 * @author c7
 */
public class QueryBuilder implements Processor {

    final static String SQL
            = "INSERT INTO product (description, price, purchase_date) "
            + "VALUES (:?description, :?price, :?purchase_date)";

    /**
     *
     * @param exchange
     * @throws Exception
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        Product product = exchange.getIn().getBody(Product.class);
        Map jdbcParametes = new HashMap();
        jdbcParametes.put("description", product.getDescription());
        jdbcParametes.put("price", product.getPrice());
        jdbcParametes.put("purchase_date", product.getPurchaseDateFormatted());
        exchange.getOut().setHeader("CamelJdbcParameters", jdbcParametes);
        /*        
        exchange.getOut().setHeader("my_description", product.getDescription());
        exchange.getOut().setHeader("my_price", product.getPrice());
        exchange.getOut().setHeader("my_purchase_date", product.getPurchaseDateFormatted());
         */
        exchange.getOut().setBody(SQL);
    }
}
