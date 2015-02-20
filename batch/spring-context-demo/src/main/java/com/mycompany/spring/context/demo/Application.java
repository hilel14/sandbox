/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.spring.context.demo;

/**
 *
 * @author hilel
 */
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackageClasses = Application.class)
public class Application {

    @Bean(name = "suffix1")
    String suffixOne() {
        return "111";
    }

    @Bean(name = "suffix2")
    String suffixTwo() {
        return "222";
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        MessagePrinter printer = context.getBean(MessagePrinter.class);
        printer.printMessage();
    }
}
