package com.epam.esm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class {@code FlywayConfig} is configuration for working with flyway.
 *
 * @author Oleksandr Myronenko
 */

@Configuration
public class FlywayConfig {
    @Value("${spring.flyway.clean-at-start}")
    private boolean cleatAtStart;

    @Value("${spring.flyway.migrate-at-start}")
    private boolean migrateAtStart;

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {
            if (cleatAtStart) {
                flyway.clean();
            }
            if (migrateAtStart) {
                flyway.migrate();
            }
        };
    }
}
