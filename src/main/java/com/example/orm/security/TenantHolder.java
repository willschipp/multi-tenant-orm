package com.example.orm.security;

public class TenantHolder {

    private static ThreadLocal<TenantContext> currentTenant = new ThreadLocal<>();

    public static void setTenantContext(String id,String key,String iv) {
        TenantContext tenantContext = new TenantContext();
        tenantContext.setId(id);
        tenantContext.setKey(key);
        tenantContext.setIv(iv);
        //set
        currentTenant.set(tenantContext);
    }

    public static void setTenantId(String tenantId)  {
        TenantContext tenantContext = new TenantContext();
        tenantContext.setId(tenantId);
        tenantContext.setKey(tenantId);
        tenantContext.setId(tenantId);
        //set
        currentTenant.set(tenantContext);
    }

    public static String getTenantId() {
        return currentTenant.get().getId();
    }

    public static TenantContext getTenantContext() {
        return currentTenant.get();
    }

    public static void reset() {
        currentTenant.remove();
    }

}
