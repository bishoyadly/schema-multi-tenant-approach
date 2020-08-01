package com.example.demo.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver {

    public static final String DEFAULT_SCHEMA = "default_schema";

    @Override
    public String resolveCurrentTenantIdentifier() {
        String currentSchema = TenantContext.getTenantSchema();
        if (Objects.nonNull(currentSchema)) {
            return currentSchema;
        } else {
            return DEFAULT_SCHEMA;
        }
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
