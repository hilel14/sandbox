/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jaxbdemo.beans;

import com.mycompany.jaxbdemo.model.Person;
import org.apache.camel.Exchange;

/**
 *
 * @author c7
 */
public class MyProcessor {

    public void makeHello(Exchange exchange, Person person) {
        String hello = "Hello " + person.getUser();
        exchange.getOut().setBody(hello);
    }
}
