package com.example.demo.config;


public class TenantContext {
    private static ThreadLocal<String> tenantSchema = new ThreadLocal<>();

    private TenantContext() {
    }

    public static String getTenantSchema() {
        return tenantSchema.get();
    }

    public static void setTenantSchema(String uuid) {
        tenantSchema.set(uuid);
    }
}
