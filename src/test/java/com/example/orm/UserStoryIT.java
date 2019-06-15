package com.example.orm;

import com.example.orm.model.Person;
import com.example.orm.model.PersonRepository;
import com.example.orm.tenancy.TenantHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserStoryIT {

    private String keyOne = "asdfer1234567890";

    @Autowired
    private PersonRepository personRepository;

    @Before
    public void before() throws Exception {
        personRepository.deleteAllInBatch();//clean up
        TenantHolder.reset();//clean up
    }

    @Test(expected = TransactionSystemException.class)
    public void test_NoTenantSetSoShouldGracefullyFail() {
        //create a person object
        Person person = new Person();
        person.setId(1);
        person.setFirstName("John");
        person.setLastName("Doe");
        //persist the object
        personRepository.save(person);
    }

    @Test(expected = TransactionSystemException.class)
    public void test_TenantSetButNotAddedShouldFail() {
        //create a tenant context
        TenantHolder.setTenantContext("1",keyOne,"blah");
        //create a person without a tenant
        Person person = new Person();
        person.setId(1);
        person.setFirstName("John");
        person.setLastName("Doe");
        //persist the object
        personRepository.save(person);
    }

    @Test
    public void test_TenantSetSoShouldBeAbleToSave() {
        //create a tenant context
        TenantHolder.setTenantContext("1",keyOne,"blah");
        //create a person with a tenant id
        Person person = new Person();
        person.setId(1);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setTenantId(TenantHolder.getTenantId());
        //persist the object
        personRepository.save(person);
        //check if it exists
        assertNotNull(personRepository.findById(1).get());
    }

    @Test
    public void test_TenantSetSoShouldBeAbleToUpdate() {
        //create a tenant context
        TenantHolder.setTenantContext("1",keyOne,"blah");
        //create a person with a tenant id
        Person person = new Person();
        person.setId(1);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setTenantId(TenantHolder.getTenantId());
        //persist the object
        personRepository.save(person);
        //check if it exists
        assertNotNull(personRepository.findById(1).get());
        //update the person
        person.setFirstName("Jane");
        //save
        //persist the object
        personRepository.save(person);
        //check if it exists
        assertNotNull(personRepository.findById(1).get());
        assertEquals("Jane",personRepository.findById(1).get().getFirstName());
    }

}
