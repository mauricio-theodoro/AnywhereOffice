package com.example.office.monitoring;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class LoggerService implements HealthIndicator {

    private final String LOGGERSERVICE = "Logger Service";

    @Override
    public Health health() {
        if(isLoggerServiceGood()) {
            return Health.up().withDetail(LOGGERSERVICE, "Servidor funcionando").build();
        }
        return Health.down().withDetail(LOGGERSERVICE, "Servidor não disponível").build();
    }

    private boolean isLoggerServiceGood() {
        //logic
        return true;
    }
}
