package com.example.orm.model;

import com.example.orm.tenancy.TenantStringEncryptor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Person extends Tenant {

    @Id
    private Integer id;

    @Column
    private String firstName;

    @Column
    @Convert(converter = TenantStringEncryptor.class)
    private String lastName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
