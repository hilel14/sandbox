package org.hilel14.springintdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.ws.SimpleWebServiceOutboundGateway;
import org.springframework.integration.ws.WebServiceHeaders;

@Configuration
@SpringBootApplication
@IntegrationComponentScan
public class SpringIntDemoApplication {

    public static void main(String[] args) {
        //SpringApplication.run(SpringIntDemoApplication.class, args);
        ConfigurableApplicationContext ctx = SpringApplication.run(SpringIntDemoApplication.class, args);
        TempConverter converter = ctx.getBean(TempConverter.class);
        System.out.println(converter.fahrenheitToCelcius(68.0f));
        ctx.close();
    }

    @MessagingGateway
    public interface TempConverter {

        @Gateway(requestChannel = "convert.input")
        float fahrenheitToCelcius(float fahren);

    }

    @Bean
    public IntegrationFlow convert() {
        return f -> f
                .transform(payload
                        -> "<FahrenheitToCelsius xmlns=\"https://www.w3schools.com/xml/\">"
                + "<Fahrenheit>" + payload + "</Fahrenheit>"
                + "</FahrenheitToCelsius>")
                .enrichHeaders(h -> h
                .header(WebServiceHeaders.SOAP_ACTION,
                        "https://www.w3schools.com/xml/FahrenheitToCelsius"))
                .handle(new SimpleWebServiceOutboundGateway(
                        "https://www.w3schools.com/xml/tempconvert.asmx"))
                .transform(Transformers.xpath("/*[local-name()=\"FahrenheitToCelsiusResponse\"]"
                        + "/*[local-name()=\"FahrenheitToCelsiusResult\"]"));
    }

}
