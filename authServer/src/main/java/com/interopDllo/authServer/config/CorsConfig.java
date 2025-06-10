package com.interopDllo.authServer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permite CORS en todas las rutas
        .allowedOrigins("http://localhost:4200") // Origen permitido (tu frontend)
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
        .allowedHeaders("Authorization", "Content-Type") // Encabezados permitidos
        .exposedHeaders("Authorization") // Encabezados expuestos
        .allowCredentials(true); // Permitir credenciales
}
}

