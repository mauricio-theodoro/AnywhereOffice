package com.example.office.monitoring;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Endpoint(id = "admin")
@Component
public class AdminActuatorEndpoint {

    @ReadOperation
    public Map<String, String> adminEndpoint(@Selector String username) {
        Map<String, String> map = new HashMap<>();
        map.put("Key", "Value");
        map.put("Username", username); // Captura o valor do caminho da URL
        return map;
    }
}
