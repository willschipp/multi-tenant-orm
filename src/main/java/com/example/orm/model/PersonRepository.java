package com.example.orm.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person,Integer> {

    List<Person> findByLastName(String lastName);
}
