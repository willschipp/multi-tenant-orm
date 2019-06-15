package com.example.orm.model;

import com.example.orm.security.TenantHolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person,Integer> {

    default List<Person> findByLastName(String lastName) {
        return findByLastNameAndTenantId(lastName, TenantHolder.getTenantId());
    }

    List<Person> findByLastNameAndTenantId(String lastName,String tenantId);
}
