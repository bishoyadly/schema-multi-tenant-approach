package com.example.demo.config;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import static com.example.demo.config.TenantSchemaResolver.DEFAULT_SCHEMA;


@Component
public class LiquibaseUtils implements TenantSchemaUtils {

    private JdbcTemplate jdbcTemplate;

    public LiquibaseUtils(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Connection getConnection(String tenantIdentifier) throws SQLException {
        DataSource dataSource = Objects.requireNonNull(jdbcTemplate.getDataSource());
        Connection connection = Objects.requireNonNull(dataSource.getConnection());
        connection.setSchema(tenantIdentifier);
        return connection;
    }

    @SneakyThrows
    @Override
    public Connection applyTenantSchemaChange(String tenantIdentifier) {
        jdbcTemplate.execute(String.format("CREATE SCHEMA IF NOT EXISTS %s", tenantIdentifier));
        Connection connection = getConnection(tenantIdentifier);
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

        String changeLogFilePath = "";
        if (tenantIdentifier.equals(DEFAULT_SCHEMA)) {
            changeLogFilePath = "db/changelog/defaultTenantSchema.xml";
        } else {
            changeLogFilePath = "db/changelog/tenantsSchema.xml";
        }
        Liquibase liquibase = new Liquibase(changeLogFilePath, new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts(), new LabelExpression());
        return connection;
    }
}
