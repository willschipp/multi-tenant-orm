package com.example.orm.model;

import com.example.orm.tenancy.TenantHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonRepositoryIT {

    @Autowired
    PersonRepository personRepository;

    @Before
    public void before() throws Exception {
        personRepository.deleteAllInBatch();
    }

    @Test
    public void test_findByLastName() {
        //set the tenant
        TenantHolder.setTenantContext("1","1234567890asdfer","blah");
        //create the array
        List<Person> persons = new ArrayList<>();
        //create 3 people
        for (int i=0;i<3;i++) {
            Person person = new Person();
            person.setId(i);
            person.setFirstName("John");
            person.setLastName("Doe" + i);
            person.setTenantId(TenantHolder.getTenantId());
            //add
            persons.add(person);
        }//end for
        //save
        personRepository.saveAll(persons);
        //now try and find by the statement
        List<Person> result = personRepository.findByLastName("Doe0");
        assertFalse(result.isEmpty());
        assertTrue(result.size() > 0);
        assertTrue(result.size() == 1);
    }

    //multi-tenant tests
    @Test(expected = TransactionSystemException.class)
    public void test_nullTenantId() {
        //save a person
        Person person = new Person();
        person.setId(1);
        person.setFirstName("John");
        person.setLastName("Doe");
        //save
        personRepository.save(person);
    }

    @Test
    public void test_tenantIdRetrieval() {
        //set the context
        TenantHolder.setTenantContext("1","1234567890asdfer","blah");
        //save a person
        Person person = new Person();
        person.setId(1);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setTenantId(TenantHolder.getTenantId());
        //save
        personRepository.save(person);
        //now retrieve with the correct tenantid
        List<Person> results = personRepository.findByLastName("Doe");
        assertFalse(results.isEmpty());
        assertTrue(results.size() == 1);
        //now RESET the tenant id
        TenantHolder.reset();
        //set something new
        TenantHolder.setTenantContext("2","asdfer1234567890","blah");
        //try again
        results = personRepository.findByLastName("Doe");
        assertTrue(results.isEmpty());
        assertFalse(results.size() == 1);
    }

    @Test
    public void test_findAll() {
        //set the context
        TenantHolder.setTenantContext("1","1234567890asdfer","blah");
        //save a person
        Person person = new Person();
        person.setId(1);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setTenantId(TenantHolder.getTenantId());
        //save
        personRepository.save(person);
        //now retrieve with the correct tenantid
        List<Person> results = personRepository.findAll();
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertTrue(results.size() == 1);//only one record
        //now add another with a different tenant id
        //set the context
        TenantHolder.setTenantContext("2","asdfer1234567890","blah");
        //save a person
        person = new Person();
        person.setId(2);
        person.setFirstName("Jane");
        person.setLastName("Doe");
        person.setTenantId(TenantHolder.getTenantId());
        //save
        personRepository.save(person);
        //check for results
        results = personRepository.findAll();
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertTrue(results.size() == 1);//only one record
    }

    @Test
    public void test_findAllNoTenant() {
        //set the context
        TenantHolder.setTenantContext("1","1234567890asdfer","blah");
        //save a person
        Person person = new Person();
        person.setId(1);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setTenantId(TenantHolder.getTenantId());
        //save
        personRepository.save(person);
        //now retrieve with the correct tenantid
        List<Person> results = personRepository.findAll();
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertTrue(results.size() == 1);//only one record
        //now add another with a different tenant id
        //set the context
        TenantHolder.reset();
        //check for results
        results = personRepository.findAll();
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void test_findAllWrongTenant() {
        //set the context
        TenantHolder.setTenantContext("1","1234567890asdfer","blah");
        //save a person
        Person person = new Person();
        person.setId(1);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setTenantId(TenantHolder.getTenantId());
        //save
        personRepository.save(person);
        //now retrieve with the correct tenantid
        List<Person> results = personRepository.findAll();
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertTrue(results.size() == 1);//only one record
        //now add another with a different tenant id
        //set the context
        TenantHolder.setTenantContext("2","asdfer1234567890","blah");
        //check for results
        results = personRepository.findAll();
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void test_countBaseOnTenantId() {
        //set the context
        TenantHolder.setTenantContext("1","1234567890asdfer","blah");
        //save a person
        Person person = new Person();
        person.setId(1);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setTenantId(TenantHolder.getTenantId());
        //save
        personRepository.save(person);
        //now retrieve with the correct tenantid
        assertTrue(personRepository.count() == 1);
        //now add another with a different tenant id
        //set the context
        TenantHolder.setTenantContext("2","asdfer1234567890","blah");
        //save a person
        person = new Person();
        person.setId(2);
        person.setFirstName("Jane");
        person.setLastName("Doe");
        person.setTenantId(TenantHolder.getTenantId());
        //save
        personRepository.save(person);
        //check for results
        assertTrue(personRepository.count() == 1);
    }

    @Test
    public void test_findOneWithTenantId() {
        //set the context
        TenantHolder.setTenantContext("1","1234567890asdfer","blah");
        //save a person
        Person person = new Person();
        person.setId(1);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setTenantId(TenantHolder.getTenantId());
        //save
        personRepository.save(person);
        //retrieve
        assertNotNull(personRepository.findById(1).get());
        assertNotNull(personRepository.getOne(1));
        //change tenancy
        TenantHolder.setTenantContext("2","asdfer1234567890","blah");
        //retrieve
        assertFalse(personRepository.findById(1).isPresent());
        assertNull(personRepository.getOne(1));

    }

}