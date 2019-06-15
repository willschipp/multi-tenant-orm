package com.example.orm.tenancy;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class InMemoryTenantService implements TenantService {

    private Map<String,TenantAttributes> tenants = Collections.synchronizedMap(new HashMap<>());

    @Override
    public TenantAttributes getTenantById(String id) {
        return tenants.get(id);
    }

    @PostConstruct
    public void load() {
        for (int i=0;i<5;i++) {
            TenantAttributes tenantAttributes = new TenantAttributes();
            tenantAttributes.setId(Integer.toString(i));
            tenantAttributes.setKey(Integer.toString(i) + "234567890asdfer");
            tenantAttributes.setIv(UUID.randomUUID().toString());
            //add
            tenants.put(tenantAttributes.getId(),tenantAttributes);
        }//end for
    }
}
