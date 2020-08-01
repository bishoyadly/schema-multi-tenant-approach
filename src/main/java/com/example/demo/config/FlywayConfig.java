package com.example.demo.config;

import com.example.demo.repositories.TenantRepository;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class FlywayConfig {

    public static final String DEFAULT_SCHEMA = "default_schema";

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public Flyway flyway(DataSource dataSource) {
        logger.info("Migrating default schema ");
        FluentConfiguration fluentConfiguration = Flyway.configure().locations("db/migration/default").dataSource(dataSource).schemas(DEFAULT_SCHEMA);
        Flyway flyway = new Flyway(fluentConfiguration);
        flyway.migrate();
        return flyway;
    }

    @Bean
    public Boolean tenantsFlyway(TenantRepository repository, DataSource dataSource) {
        repository.findAll().forEach(tenant -> {
            String schema = tenant.getSchemaName();
            FluentConfiguration fluentConfiguration = Flyway.configure().locations("db/migration/tenants").dataSource(dataSource).schemas(schema);
            Flyway flyway = new Flyway(fluentConfiguration);
            flyway.migrate();
        });
        return true;
    }

}
