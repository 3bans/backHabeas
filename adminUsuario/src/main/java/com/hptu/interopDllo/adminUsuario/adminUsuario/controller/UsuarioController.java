package com.hptu.interopDllo.adminUsuario.adminUsuario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hptu.interopDllo.adminUsuario.adminUsuario.dto.UsuarioDTO;
import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.Usuarios;
import com.hptu.interopDllo.adminUsuario.adminUsuario.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/listaUsuario")
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.obtenerUsuariosConRoles();
    }

      @PostMapping("/crear")
    public ResponseEntity<Usuarios> crearUsuario(@RequestBody Usuarios usuario) {
        Usuarios nuevoUsuario = usuarioService.crearUsuario(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }
}