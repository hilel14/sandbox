/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.spring.context.demo.mservices;

import com.mycompany.spring.context.demo.MessageService;
import org.springframework.stereotype.Component;

/**
 *
 * @author hilel
 */
@Component(value = "service1")
public class FirstMessageService implements MessageService {

    @Override
    public String getMessage() {
        return "This is service one";
    }

}