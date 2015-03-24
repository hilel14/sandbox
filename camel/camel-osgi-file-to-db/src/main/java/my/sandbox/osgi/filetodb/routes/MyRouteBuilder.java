package my.sandbox.osgi.filetodb.routes;

import my.sandbox.osgi.filetodb.model.Product;
import my.sandbox.osgi.filetodb.processors.PriceCalculator;
import my.sandbox.osgi.filetodb.processors.QueryBuilder;
import org.apache.camel.PropertyInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;

public class MyRouteBuilder extends RouteBuilder {

    @PropertyInject("inputFolder")
    private String inputFolder;

    @Override
    public void configure() throws Exception {

        from(inputFolder)
                .routeId("my-route-1")
                .split(body().tokenize("\n")).streaming()
                .choice().when(header("CamelSplitIndex").isEqualTo(0)).log("do some setup").end()
                .unmarshal().bindy(BindyType.Csv, Product.class)
                .log("${body.description}")
                .bean(PriceCalculator.class)
                .bean(QueryBuilder.class)
                .marshal().bindy(BindyType.Csv, Product.class)
                .to("file:///var/opt/data/out?fileName=${in.header.description}")
                .setBody(header("insert.query"))
                .log("${body}")
                .to("jdbc:product")
                .choice().when(header("CamelSplitComplete")).log("stop the route").end();
    }
}
