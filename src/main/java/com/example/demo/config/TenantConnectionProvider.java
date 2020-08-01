package com.example.demo.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

import static com.example.demo.config.TenantSchemaResolver.DEFAULT_SCHEMA;

@Component
@Slf4j
public class TenantConnectionProvider implements MultiTenantConnectionProvider {

    private transient TenantSchemaUtils tenantSchemaUtils;

    public TenantConnectionProvider(@Qualifier("liquibaseUtils") TenantSchemaUtils tenantSchemaUtils) {
        this.tenantSchemaUtils = tenantSchemaUtils;
    }

    @Override
    public Connection getAnyConnection() {
        return tenantSchemaUtils.applyTenantSchemaChange(DEFAULT_SCHEMA);
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @SneakyThrows
    @Override
    public Connection getConnection(String tenantIdentifier) {
        return tenantSchemaUtils.applyTenantSchemaChange(tenantIdentifier);
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        connection.setSchema(DEFAULT_SCHEMA);
        releaseAnyConnection(connection);
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }
}
