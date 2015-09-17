/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jaxbdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author c7
 */
public class Main {

    static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // Create camel-spring Main instance.
        // By default it will load application context from files in META-INF/spring/
        // This method will have the same effect as: mvn camel:run
        org.apache.camel.spring.Main main = new org.apache.camel.spring.Main();
        // enable hangup support so you can press ctrl + c to terminate the JVM
        main.enableHangupSupport();
        // run until you terminate the JVM
        System.out.println("Starting Camel. Use ctrl + c to terminate the JVM.\n");
        try {
            main.run(args);
        } catch (Exception ex) {
            logger.error(null, ex);
        }
    }
}
