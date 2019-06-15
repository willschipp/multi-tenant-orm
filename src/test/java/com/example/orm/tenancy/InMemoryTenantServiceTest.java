package com.example.orm.tenancy;

import org.junit.Test;

import static org.junit.Assert.*;

public class InMemoryTenantServiceTest {

    @Test
    public void getTenantById() {
        InMemoryTenantService inMemoryTenantService = new InMemoryTenantService();
        assertNull(inMemoryTenantService.getTenantById("1"));
    }

    @Test
    public void load() {
        InMemoryTenantService inMemoryTenantService = new InMemoryTenantService();
        assertNull(inMemoryTenantService.getTenantById("1"));
        inMemoryTenantService.load();
        assertNotNull(inMemoryTenantService.getTenantById("1"));
    }
}