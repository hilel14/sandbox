package com.mycompany.jaxbdemo.processors;

import com.mycompany.jaxbdemo.model.Employee;
import java.util.HashMap;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 *
 * @author c7
 */
public class PrepareStatement implements Processor {

    final String sql = "INSERT INTO employee (job_title, employee_name) VALUES (:?jobTitle, :?employeeName)";

    @Override
    public void process(Exchange exchange) {
        Employee employee = exchange.getIn().getBody(Employee.class);
        Map jdbcParameters = new HashMap();
        jdbcParameters.put("jobTitle", employee.getJobTitle());
        jdbcParameters.put("employeeName", employee.getEmployeeName());
        exchange.getOut().setHeader("CamelJdbcParameters", jdbcParameters);
        exchange.getOut().setBody(sql);
    }
}
