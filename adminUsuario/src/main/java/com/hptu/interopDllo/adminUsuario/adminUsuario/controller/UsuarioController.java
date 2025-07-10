package com.hptu.interopDllo.adminUsuario.adminUsuario.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hptu.interopDllo.adminUsuario.adminUsuario.dto.UsuarioDTO;
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
}