/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.spring.sandbox.spring.batch.demo.writers;

import javax.sql.DataSource;
import my.spring.sandbox.spring.batch.demo.model.Person;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

/**
 *
 * @author hilel
 */
public class PersonWriter extends JdbcBatchItemWriter<Person> {

    public void setPeopleDataSource(final DataSource peopleDataSource) {
        setDataSource(peopleDataSource);
    }

    public PersonWriter() {
        setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
        setSql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)");
    }
}
