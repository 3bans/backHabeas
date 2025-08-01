package com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.services;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.request.ConsentRequest;
import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.entity.HabeasDataText;
import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.repository.HabeasDataTextRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@RequiredArgsConstructor
@Slf4j
public class HabeasDataTextService {
 private final HabeasDataTextRepository repository;

    /**
     * Recupera la plantilla de texto por su ID.
     */
    public String getTemplateById(Long id) {
        log.info("Obteniendo plantilla para id={}", id);
        HabeasDataText data = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                "No se encontró plantilla HABEAS_DATA_APP con id " + id));
        log.info("Plantilla obtenida: {}", data.getTexto());
        return data.getTexto();
    }

    /**
     * Rellena la plantilla con los datos del request y devuelve el texto completo.
     * Usa Objects.toString para asegurar no imprimir 'null'.
     */
    public String renderConsent(Long id, ConsentRequest req) {
        log.info("Renderizando consent id={} con request={}", id, req);
        String template = getTemplateById(id);

        Object[] args = new Object[]{
            Objects.toString(req.getProfesionalNombre(), ""),
            Objects.toString(req.getProfesionalDocumento(), ""),
            req.getDia(),
            Objects.toString(req.getMes(), ""),
            req.getAno(),
            Objects.toString(req.getPacienteNombre(), ""),
            Objects.toString(req.getPacienteTipoDocumento(), ""),
            Objects.toString(req.getPacienteDocumento(), ""),
            Objects.toString(req.getRepresentanteNombre(), ""),
            Objects.toString(req.getRepresentanteTipoDocumento(), ""),
            Objects.toString(req.getRepresentanteDocumento(), "")
        };

        String result = String.format(template, args);
        // Limpieza de espacios múltiples para mejor legibilidad en consola
        log.info("Texto renderizado: {}", result.replaceAll("\s+", " "));
        return result;
    }
}

