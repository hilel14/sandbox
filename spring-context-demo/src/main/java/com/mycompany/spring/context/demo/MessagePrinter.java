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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MessagePrinter {

    final private MessageService service;
    final private String suffix;

    @Autowired
    public MessagePrinter(@Qualifier("service1") MessageService service, @Qualifier("suffix2") String suffix) {
        this.service = service;
        this.suffix = suffix;
    }

    public void printMessage() {
        System.out.println(this.service.getMessage() + " " + suffix);
    }
}
