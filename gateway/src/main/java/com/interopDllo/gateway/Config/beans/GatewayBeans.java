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

 .route("adminUsuario-roles", route -> route
                .path("/api/roles/activos")
                 .filters(f -> f
                    .filter(authFilter)
                    .rewritePath("/habeasLogin/api/(?<segment>.*)", "/api/${segment}")
                )
                .uri("lb://adminUsuario") )
 .route("adminUsuario-roles", route -> route
                .path("/api/roles/activos")
                 .filters(f -> f
                    .filter(authFilter)
                    .rewritePath("/habeasLogin/api/(?<segment>.*)", "/api/${segment}")
                )
                .uri("lb://adminUsuario") )



 .route("pacientesHabeas-motivos", route -> route
                .path("/api/habeas/motivos")
                 .filters(f -> f
                    .filter(authFilter)
                    .rewritePath("/habeasLogin/api/(?<segment>.*)", "/api/${segment}")
                )
                .uri("lb://pacientesHabeas") )


          







                 .route("adminUsuario-puntoServicio", route -> route
                .path("/api/puntoServicio/activos")
                 .filters(f -> f
                    .filter(authFilter)
                    .rewritePath("/habeasLogin/api/(?<segment>.*)", "/api/${segment}")
                )
                .uri("lb://adminUsuario") )

            .route("adminUsuario-crear", route -> route
                .path("/api/usuarios/crear")
                 .filters(f -> f
                    .filter(authFilter)
                    .rewritePath("/habeasLogin/api/(?<segment>.*)", "/api/${segment}")
                )
                .uri("lb://adminUsuario") )

       .route("adminUsuario-actualizar", route -> route
                .path("/api/usuarios/actualizar")
                 .filters(f -> f
                    .filter(authFilter)
                    .rewritePath("/habeasLogin/api/(?<segment>.*)", "/api/${segment}")
                )
                .uri("lb://adminUsuario") )
                .route("adminUsuario-activos", route -> route
                .path("/api/usuarios/activos")
                 .filters(f -> f
                    .filter(authFilter)
                    .rewritePath("/habeasLogin/api/(?<segment>.*)", "/api/${segment}")
                )
                .uri("lb://adminUsuario") )

            .route("adminUsuario-registrar", route -> route
                .path("/api/medicos/registrar")
                 .filters(f -> f
                    .filter(authFilter)
                    .rewritePath("/habeasLogin/api/(?<segment>.*)", "/api/${segment}")
                )
                .uri("lb://adminUsuario") )

                 .route("adminUsuario-activos", route -> route
                .path("/api/medicos/activos")
                 .filters(f -> f
                    .filter(authFilter)
                    .rewritePath("/habeasLogin/api/(?<segment>.*)", "/api/${segment}")
                )
                .uri("lb://adminUsuario") )

                 .route("adminUsuario-actualizar", route -> route
                .path("/api/medicos/actualizar")
                 .filters(f -> f
                    .filter(authFilter)
                    .rewritePath("/habeasLogin/api/(?<segment>.*)", "/api/${segment}")
                )
                .uri("lb://adminUsuario") )
                
                .route("adminUsuario-secretarias", route -> route
                .path("/api/usuarios/secretarias")
                 .filters(f -> f
                    .filter(authFilter)
                    .rewritePath("/habeasLogin/api/(?<segment>.*)", "/api/${segment}")
                )
                .uri("lb://adminUsuario") )

                 .route("adminUsuario-asignacionMedicos", route -> route
                .path("/api/asignacionMedicos/asignar")
                 .filters(f -> f
                    .filter(authFilter)
                    .rewritePath("/habeasLogin/api/(?<segment>.*)", "/api/${segment}")
                )
                .uri("lb://adminUsuario") )
.route("adminUsuario-medicos", route -> route
    .path("/api/asignacionMedicos/medicoSecretaria/**")
    .filters(f -> f
        .filter(authFilter)
        .rewritePath("/habeasLogin/api/(?<segment>.*)", "/api/${segment}")
    )
    .uri("lb://adminUsuario")
)
.route("adminUsuario-medicoSecretaria", route -> route
    .path("/api/asignacionMedicos/secretariAsignada/**")
    .filters(f -> f
        .filter(authFilter)
        .rewritePath("/habeasLogin/api/(?<segment>.*)", "/api/${segment}")
    )
    .uri("lb://adminUsuario")  
)
  .route("menuHabeas-consultar", r -> r
      .path("/api/habeas/consultar")            // mismo prefix público que usas en Postman
      .filters(f -> f.filter(authFilter))        // no necesita rewrite si el mapping interno es igual
      .uri("lb://menuHabeas")
    )

.route("menuHabeas-todos", route -> route
    .path("/api/habeas/todos")
    .filters(f -> f
        .filter(authFilter)
        .rewritePath("/habeasLogin/api/(?<segment>.*)", "/api/${segment}")
    )
    .uri("lb://menuHabeas")  
)

            .build();
    }
    
    
}