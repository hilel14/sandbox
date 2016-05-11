package com.mycompany.jaxbdemo.beans;

import com.mycompany.jaxbdemo.model.Employee;
import com.mycompany.jaxbdemo.model.Person;

/**
 *
 * @author hilel
 */
public class PersonToEmployee {

    public Employee hireThisMan(Person person) {
        Employee employee = new Employee();
        employee.setEmployeeName(person.getFirstName());
        employee.setJobTitle("Manager");
        return employee;
    }
}
