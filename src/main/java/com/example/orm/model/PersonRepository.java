package com.example.orm.model;

import com.example.orm.tenancy.TenantHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person,Integer> {

    default List<Person> findByLastName(String lastName) {
        return findByLastNameAndTenantId(lastName, TenantHolder.getTenantId());
    }

    List<Person> findByLastNameAndTenantId(String lastName,String tenantId);

    @Override
    @Query("select e from #{#entityName} e where e.tenantId = ?#{T(com.example.orm.tenancy.TenantHolder).getTenantId()}")
    List<Person> findAll();

    @Override
    @Query("select count(e) from #{#entityName} e where e.tenantId = ?#{T(com.example.orm.tenancy.TenantHolder).getTenantId()}")
    long count();

    @Override
    @Query("select e from #{#entityName} e where e.id = ?1 and e.tenantId = ?#{T(com.example.orm.tenancy.TenantHolder).getTenantId()}")
    Person getOne(Integer integer);

    @Override
    @Query("select e from #{#entityName} e where e.id = ?1 and e.tenantId = ?#{T(com.example.orm.tenancy.TenantHolder).getTenantId()}")
    Optional<Person> findById(Integer integer);
}
