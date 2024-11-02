package com.example.office.monitoring;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DatabaseService implements HealthIndicator {

    private final String DATABASESERVICE = "Database Service";

    @Override
    public Health health() {
        if(isDatabaseHealthGood()) {
            return Health.up().withDetail(DATABASESERVICE, "Servidor funcionando").build();
        }
        return Health.down().withDetail(DATABASESERVICE, "Servidor não disponível").build();
    }

    private boolean isDatabaseHealthGood() {
        //logic
        return true;
    }
}
