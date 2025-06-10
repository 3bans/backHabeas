package com.interopDllo.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import com.interopDllo.gateway.Services.CacheService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;
import org.reactivestreams.Publisher;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@Component
public class CacheFilter implements GatewayFilter {

    private  CacheService cacheService;

  

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Clave única para almacenar la respuesta en Redis
        final String cacheKey = "geriatria-actividades";
        System.out.println("🟢 CacheFilter: Iniciando para clave " + cacheKey);

        // Verificar si la respuesta ya está en caché
        Object cachedResponse = cacheService.getCachedResponse(cacheKey);
        if (cachedResponse instanceof String cachedString) {
            if (cachedString.trim().isEmpty()) {
                System.out.println("🚨 CacheFilter: Respuesta vacía, no se devolverá desde caché.");
            } else {
                System.out.println("⚡ CacheFilter: Respuesta encontrada en caché");
                exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
                byte[] bytes = cachedString.getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                return exchange.getResponse().writeWith(Mono.just(buffer)); // Devuelve la respuesta almacenada en Redis
            }
        }

        // Si no hay respuesta en caché, interceptamos la respuesta para almacenarla
        ServerHttpResponse originalResponse = exchange.getResponse();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                return Flux.from(body)
                    .collectList() // Recopilar todos los DataBuffer en una lista
                    .flatMap(dataBuffers -> {
                        // Construir la respuesta completa como String
                        StringBuilder responseBuilder = new StringBuilder();
                        for (DataBuffer dataBuffer : dataBuffers) {
                            byte[] content = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(content);
                            responseBuilder.append(new String(content, StandardCharsets.UTF_8));
                        }
                        String responseString = responseBuilder.toString();
                        System.out.println("🟠 CacheFilter: Respuesta obtenida -> " + responseString);
                        
                        // Guardar en Redis solo si la respuesta no está vacía
                        if (!responseString.trim().isEmpty()) {
                            System.out.println("🟢 CacheFilter: Guardando respuesta en Redis...");
                            cacheService.saveResponse(cacheKey, responseString, 3600);
                            System.out.println("🟢 CacheFilter: Respuesta guardada en Redis con clave: " + cacheKey);
                        } else {
                            System.out.println("🚨 CacheFilter: La respuesta de la API está vacía, no se guardará en Redis.");
                        }
                        
                        
                        // Devolver los DataBuffer originales para que la respuesta se envíe al cliente
                        return super.writeWith(Flux.fromIterable(dataBuffers));
                    });
            }
        };

        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }
}
