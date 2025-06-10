package com.interopDllo.gateway.filters;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.interopDllo.gateway.dtos.TokenDto;

import reactor.core.publisher.Mono;

@Component
public class AuthFilter implements GatewayFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);
    
    private final WebClient webClient;
    private static final String AUTH_VALIDATE_URI = "http://localhost:3030/authServer/auth/jwt";

    public AuthFilter() {
        this.webClient = WebClient.builder().build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Permitir solicitudes OPTIONS sin autenticación
        if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
            return chain.filter(exchange); // <-- ¡Cambio clave!
        }

        logger.info("🔍 Iniciando validación de autenticación...");

        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            logger.warn("⛔ No se encontró el header 'Authorization'");
            return onError(exchange, "Falta el header de autorización");
        }

        final String tokenHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            logger.warn("⛔ El token no tiene el formato correcto: {}", tokenHeader);
            return onError(exchange, "Token inválido");
        }

        final String token = tokenHeader.substring(7);
        logger.info("✅ Token recibido: {}", token);

        return this.webClient.post()
            .uri(AUTH_VALIDATE_URI)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .retrieve()
            .bodyToMono(TokenDto.class)
            .flatMap(response -> {
                logger.info("✅ Token validado correctamente");
                return chain.filter(exchange);
            })
            .onErrorResume(error -> {
                logger.error("❌ Error en validación del token: {}", error.getMessage());
                return onError(exchange, "Token inválido o expirado");
            });
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message) {
        logger.error("🚨 Autenticación fallida: {}", message);
        
        var response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json");
        
        String jsonBody = String.format("{\"code\":401,\"message\":\"%s\"}", message);
        DataBuffer buffer = response.bufferFactory().wrap(jsonBody.getBytes(StandardCharsets.UTF_8));
        
        return response.writeWith(Mono.just(buffer));
    }
}