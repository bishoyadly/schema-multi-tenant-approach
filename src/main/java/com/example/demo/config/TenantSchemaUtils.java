package com.example.demo.config;

import org.springframework.stereotype.Component;

import java.sql.Connection;

@Component
public interface TenantSchemaUtils {

    Connection applyTenantSchemaChange(String tenantIdentifier);
}
