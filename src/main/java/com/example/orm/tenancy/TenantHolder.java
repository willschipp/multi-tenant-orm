package com.example.orm.tenancy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TenantHolder {

    private static final Log logger = LogFactory.getLog(TenantHolder.class);

    private static ThreadLocal<TenantAttributes> currentTenant = new ThreadLocal<>();

    public static void setTenantContext(String id,String key,String iv) {
        TenantAttributes userTenant = new TenantAttributes();
        userTenant.setId(id);
        userTenant.setKey(key);
        userTenant.setIv(iv);
        //set
        currentTenant.set(userTenant);
    }

    public static void setTenantId(String tenantId)  {
        TenantAttributes userTenant = new TenantAttributes();
        userTenant.setId(tenantId);
        userTenant.setKey(tenantId);
        userTenant.setId(tenantId);
        //set
        currentTenant.set(userTenant);
    }

    public static String getTenantId() {
        if (currentTenant.get() != null) {
            return currentTenant.get().getId();
        }//end if
        logger.warn("no current tenant set; returning null");
        //return
        return null;
    }

    public static TenantAttributes getTenantContext() {
        return currentTenant.get();
    }

    public static void reset() {
        currentTenant.remove();
    }

}
