package com.interopDllo.authServer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


import org.springframework.stereotype.Component;

@Component // Anotación clave para que Spring lo detecte
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private String secret;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
