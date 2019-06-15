package com.example.orm.security;

import com.example.orm.tenancy.TenantHolder;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTenantHolderTest {

    @Test
    public void setTenantId() {
        TenantHolder.setTenantId("1");
        assertEquals("1",TenantHolder.getTenantId());
    }

    @Test
    public void getTenantId() {
        TenantHolder.setTenantId("1");
        assertEquals("1",TenantHolder.getTenantId());
    }

    @Test
    public void reset() {
        TenantHolder.setTenantId("1");
        assertEquals("1",TenantHolder.getTenantId());
        TenantHolder.reset();
        assertNull(TenantHolder.getTenantContext());
    }
}