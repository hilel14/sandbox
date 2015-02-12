package my.camel.sandbox.filedemo.routes;

import my.camel.sandbox.filedemo.model.Product;
import my.camel.sandbox.filedemo.processors.MyProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;

/**
 * 
 * mvn camel:run
 *
 */
public class MyRouteBuilder extends RouteBuilder {

    MyProcessor myProcessor = new MyProcessor();

    @Override
    public void configure() throws Exception {

        from("file:///var/opt/data/in")
                .routeId("my-route-1")
                .split(body().tokenize("\n")).streaming()
                .unmarshal().bindy(BindyType.Csv, Product.class)
                .process(myProcessor)
                .marshal().bindy(BindyType.Csv, Product.class)
                .to("file:///var/opt/data/out?fileName=${in.header.description}");
    }
}
