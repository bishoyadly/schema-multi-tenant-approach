package com.example.demo.config;

import lombok.SneakyThrows;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Objects;

import static com.example.demo.config.TenantSchemaResolver.DEFAULT_SCHEMA;

@Component
public class FlywayUtils implements TenantSchemaUtils {

    private DataSource dataSource;

    @Autowired
    public FlywayUtils(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @SneakyThrows
    @Override
    public Connection applyTenantSchemaChange(String tenantIdentifier) {
        String migrationDirPath = "";
        if (tenantIdentifier.equals(DEFAULT_SCHEMA)) {
            migrationDirPath = "db/migration/default";
        } else {
            migrationDirPath = "db/migration/tenants";
        }
        FluentConfiguration fluentConfiguration = Flyway.configure().locations(migrationDirPath).dataSource(dataSource).schemas(tenantIdentifier);
        Flyway flyway = new Flyway(fluentConfiguration);
        flyway.migrate();

        Connection connection = Objects.requireNonNull(dataSource.getConnection());
        connection.setSchema(tenantIdentifier);
        return connection;
    }
}
