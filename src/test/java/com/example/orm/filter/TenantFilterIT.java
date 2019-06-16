package com.example.orm.filter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.ServletException;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TenantFilterIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void doFilter() throws Exception {
        //set a tenant id header and get a result
        mockMvc.perform(get("/persons").header("x-tenant-id","1")).andExpect(status().isOk());
    }

    @Test(expected = ServletException.class)
    public void doFilterNoHeader() throws Exception {
        //set a tenant id header and get a result
        mockMvc.perform(get("/persons"));
    }
}