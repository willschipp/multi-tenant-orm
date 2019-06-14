package com.example.orm.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonRepositoryIT {

    @Autowired
    PersonRepository personRepository;

    @Test
    public void test_findByLastName() {
        List<Person> persons = new ArrayList<>();
        //create 3 people
        for (int i=0;i<3;i++) {
            Person person = new Person();
            person.setId(i);
            person.setFirstName("John");
            person.setLastName("Doe" + i);
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

}