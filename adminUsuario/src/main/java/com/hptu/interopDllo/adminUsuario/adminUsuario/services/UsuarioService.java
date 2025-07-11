package com.hptu.interopDllo.adminUsuario.adminUsuario.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hptu.interopDllo.adminUsuario.adminUsuario.Repository.UsuarioRepository;
import com.hptu.interopDllo.adminUsuario.adminUsuario.dto.UsuarioDTO;
import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.Usuarios;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> obtenerUsuariosConRoles() {
    List<Object[]> rawData = usuarioRepository.obtenerUsuariosConRolesRaw();
    List<UsuarioDTO> resultado = new ArrayList<>();

    for (Object[] fila : rawData) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario((String) fila[0]);
        dto.setTipoIdUsuario((String) fila[1]);
        dto.setNombre((String) fila[2]);
        dto.setPuntoAtencion((String) fila[3]);
        dto.setDepartamento((String) fila[4]);
        dto.setSeccion((String) fila[5]);
        dto.setNombreRol((String) fila[6]);
        dto.setEstado((String) fila[7]);
        resultado.add(dto);
    }

    return resultado;
}

public Usuarios crearUsuario(Usuarios usuario) {
    return usuarioRepository.save(usuario);
}


}