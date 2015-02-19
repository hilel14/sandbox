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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
//import javax.inject.Inject; // Java EE
//import javax.annotation.Resource;
//import org.springframework.beans.factory.annotation.Qualifier;

@Component
@PropertySource("classpath:printer.properties")
public class MessagePrinter {

    private final MessageService service;

    @Autowired
    public MessagePrinter(ApplicationContext context, Environment environment) {
        //service = context.getBean(MessageService.class); // NoUniqueBeanDefinitionException
        service = context.getBean(environment.getProperty("messageService"), MessageService.class);
    }

    public void printMessage() {
        System.out.println(service.getMessage());
    }

}
