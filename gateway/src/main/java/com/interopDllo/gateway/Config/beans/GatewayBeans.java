package com.interopDllo.gateway.Config.beans;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.interopDllo.gateway.filters.AuthFilter;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class GatewayBeans {

    private  AuthFilter authFilter;

    @Bean
    @Profile("eureka-off")
    public RouteLocator routeLocatorEurekaOff(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(route -> route
                .path("/geriatria/actividades/all")
                .uri("http://localhost:8081")
            )
            .build();
    }

    @Bean
    @Profile("eureka-on")
    public RouteLocator routeLocatorEurekaOn(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(route -> route
                .path("/geriatria/actividades/all")
                .uri("lb://geriatriaGestionRegistro")
            )
            .build();
    }

    @Bean
    @Profile("auth2")
    public RouteLocator routeLocatorOauth2(RouteLocatorBuilder builder) {
        return builder.routes()
            // 1. Endpoint del authServer para generar token JWT (interno)
            .route("auth-login", route -> route
                .path("/authServer/auth/login")
                .uri("lb://authServer")
            )
    
            // 2. Endpoint público del habeasLogin (login que valida con LDAP y llama al authServer)
            .route("habeas-login-public", route -> route
                .path("/habeasLogin/api/auth/login") // SOLO este es público
                .uri("lb://habeasLogin")
            )
    
            // 3. Rutas protegidas con JWT
            .route("habeas-protected", route -> route
                .path("/habeasLogin/api/**")
                .filters(f -> f
                    .filter(authFilter)
                    .rewritePath("/habeasLogin/api/(?<segment>.*)", "/api/${segment}")
                )
                .uri("lb://menuHabeas")
            )

             .route("admPacientes-buscar", route -> route
                .path("/api/paciente/buscar")
                 .filters(f -> f
                    .filter(authFilter)
                    .rewritePath("/habeasLogin/api/(?<segment>.*)", "/api/${segment}")
                )
                .uri("lb://admPacientes")
            )

            .route("admPacientes-servicios", route -> route
                .path("/api/oracle/servicios")
                 .filters(f -> f
                    .filter(authFilter)
                    .rewritePath("/habeasLogin/api/(?<segment>.*)", "/api/${segment}")
                )
                .uri("lb://admPacientes") )
                .route("pacientesHabeas-estado", route -> route
                .path("/api/habeas/existe")
                 .filters(f -> f
                    .filter(authFilter)
                    .rewritePath("/habeasLogin/api/(?<segment>.*)", "/api/${segment}")
                )
                .uri("lb://pacientesHabeas") )

                  .route("pacientesHabeas-registrar", route -> route
                .path("/api/habeas/registrar")
                 .filters(f -> f
                    .filter(authFilter)
                    .rewritePath("/habeasLogin/api/(?<segment>.*)", "/api/${segment}")
                )
                .uri("lb://pacientesHabeas") )

                  .route("adminUsuario-listaUsuarios", route -> route
                .path("/api/usuarios/listaUsuario")
                 .filters(f -> f
                    .filter(authFilter)
                    .rewritePath("/habeasLogin/api/(?<segment>.*)", "/api/${segment}")
                )
                .uri("lb://adminUsuario") )
            .build();
    }
    
    
}