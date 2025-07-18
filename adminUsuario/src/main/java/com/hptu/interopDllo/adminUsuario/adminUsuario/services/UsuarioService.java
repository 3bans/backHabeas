package com.hptu.interopDllo.adminUsuario.adminUsuario.services;

import java.math.BigDecimal;
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

    /**
     * Mapea los resultados del query nativo a una lista de UsuarioDTO.
     */
 public List<UsuarioDTO> obtenerUsuariosConRoles() {
    List<Object[]> rawData = usuarioRepository.obtenerUsuariosConRolesRaw();
    List<UsuarioDTO> resultado = new ArrayList<>();

    for (Object[] fila : rawData) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario((String) fila[0]);
        dto.setTipoIdUsuario((String) fila[1]);
        dto.setNombre((String) fila[2]);
        dto.setPuntoAtencion((String) fila[3]);

        // Conversión robusta
        Object rawIdPunto = fila[4];
        Object rawRolId = fila[8];

        dto.setIdPuntoAtencion(rawIdPunto instanceof BigDecimal
                ? ((BigDecimal) rawIdPunto).intValue()
                : (Integer) rawIdPunto);

        dto.setDepartamento((String) fila[5]);
        dto.setSeccion((String) fila[6]);
        dto.setNombreRol((String) fila[7]);

        dto.setRolId(rawRolId instanceof BigDecimal
                ? ((BigDecimal) rawRolId).intValue()
                : (Integer) rawRolId);

        dto.setEstado((String) fila[9]);
        resultado.add(dto);
    }

    return resultado;
}


    /**
     * Inserta un nuevo usuario en la base de datos.
     */
    public Usuarios crearUsuario(Usuarios usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuarios actualizarUsuario(Usuarios usuario) {
    // Validación básica (opcional)
    if (!usuarioRepository.existsById(usuario.getIdUsuario())) {
        throw new RuntimeException("Usuario no encontrado con ID: " + usuario.getIdUsuario());
    }

    // Guarda y retorna
    return usuarioRepository.save(usuario);
    }

     public List<Usuarios> obtenerUsuariosMedicosActivos() {
        return usuarioRepository.obtenerUsuariosMedicosActivos();
    }
}


