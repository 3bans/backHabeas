package com.interopDllo.authServer.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter {

  @Autowired
    private JwtService jwtService;  // Servicio para validar el JWT

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Eliminar el prefijo "Bearer "

            try {
                // Validar el token y obtener los datos del usuario (si es válido)
                if (jwtService.validateToken(token)) {
                    // Si el token es válido, puedes colocar el usuario en el contexto de seguridad
                    // por ejemplo, usando un AuthenticationManager
                    // O puedes pasar los detalles del usuario a través de un filtro de seguridad.
                    // Ejemplo:
                    // UserDetails userDetails = jwtService.extractUserDetails(token);
                    // SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token inválido");
                    return;
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Error al procesar el token");
                return;
            }
        }

        filterChain.doFilter(request, response);  // Continuar con la cadena de filtros
    }

   
    @Override
    public void destroy() {
        // Liberar recursos si es necesario
    }

}
