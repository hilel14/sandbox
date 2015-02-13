package my.camel.sandbox.filedemo.routes;

import my.camel.sandbox.filedemo.model.Product;
import my.camel.sandbox.filedemo.processors.MyProcessor;
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
                .bean(MyProcessor.class)
                .marshal().bindy(BindyType.Csv, Product.class)
                .to("file:///var/opt/data/out?fileName=${in.header.description}")
                .choice().when(header("CamelSplitComplete")).log("stop the route").end();
    }
}
