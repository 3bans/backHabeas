package com.hptu.interopDllo.habeasData.habeasLogin.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Habeas Login")
                        .description("Documentación interactiva de la API de Login Habeas")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo Desarrollo")
                                .email("rpaadres@hptu.org.co")));
    }
}
