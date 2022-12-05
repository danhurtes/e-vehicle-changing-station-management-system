package com.netvalue.evehicleschargemeter.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "basic-auth")
public class BasicAuthUserProperties {
    private Map<String, User> users;

    @Data
    public static class User {
        private String username;
        private String password;
        private String[] roles;
    }
}
