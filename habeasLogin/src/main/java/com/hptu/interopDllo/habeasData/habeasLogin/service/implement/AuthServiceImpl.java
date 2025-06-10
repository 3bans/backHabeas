package com.hptu.interopDllo.habeasData.habeasLogin.service.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hptu.interopDllo.habeasData.habeasLogin.dto.request.LoginRequest;
import com.hptu.interopDllo.habeasData.habeasLogin.dto.response.LoginUser;
import com.hptu.interopDllo.habeasData.habeasLogin.dto.response.ResponseDto;
import com.hptu.interopDllo.habeasData.habeasLogin.repository.UserRepository;
import com.hptu.interopDllo.habeasData.habeasLogin.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    public AuthServiceImpl(RestTemplate restTemplate, UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseDto callExternalService(String url, Object requestBody) {
        if (!(requestBody instanceof LoginRequest loginRequest)) {
            logger.warn("Request inválido: No es instancia de LoginRequest");
            return ResponseDto.builder()
                    .code(404)
                    .description("Request inválido")
                    .build();
        }
    
        logger.info("Iniciando llamada al servicio externo: {}", url);
    
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, headers);
    
        ResponseEntity<LoginUser> response;
        try {
            response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    LoginUser.class
            );
            logger.info("Respuesta recibida del servicio externo: Status Code = {}", response.getStatusCode());
        
            // 🔥 Loggear el body completo
            LoginUser responseBody = response.getBody();
            if (responseBody != null) {
                logger.info("Contenido del body recibido: {}", responseBody);
            } else {
                logger.warn("Body de la respuesta es nulo.");
            }
        
        } catch (Exception ex) {
            logger.error("Error al llamar servicio externo: {}", ex.getMessage());
            return ResponseDto.builder()
                    .code(404)
                    .description("Fallo al llamar servicio externo: " + ex.getMessage())
                    .build();
        }
    
        LoginUser responseBody = response.getBody();
        logger.info("Respuesta recibida del servicio externo:", response.getBody());
        if (responseBody == null) {
            logger.warn("Servicio externo no retornó datos válidos (cuerpo nulo)");
            return ResponseDto.builder()
                    .code(404)
                    .description("Servicio externo no retornó datos válidos")
                    .build();
        }
    
        if (!"200".equals(responseBody.getCode())) {
            logger.warn("Servicio externo retornó error: {}", responseBody.getDescription());
            return ResponseDto.builder()
                    .code(404)
                    .description(responseBody.getDescription())
                    .build();
        }
    
        String idUsuario = loginRequest.getUser();
        logger.info("Buscando usuario en base de datos: {}", idUsuario);
    
        boolean exists = this.userRepository.findByIdUsuario(idUsuario).isPresent();
        if (!exists) {
            logger.warn("Usuario no encontrado en la base de datos: {}", idUsuario);
            return ResponseDto.builder()
                    .code(404)
                    .description("Usuario no encontrado en la base de datos")
                    .build();
        }
    
        logger.info("Usuario autenticado exitosamente: {}", idUsuario);
        return ResponseDto.builder()
                .code(200)
                .description("ok")
                .build();
    }
}