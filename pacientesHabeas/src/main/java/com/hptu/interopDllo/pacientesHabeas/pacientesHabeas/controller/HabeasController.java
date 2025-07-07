package com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody; // ✅ ESTA ES LA CORRECTA

import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.request.HabeasRequest;
import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.request.mensajeRequest;
import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.response.HabeasResponse;
import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.response.ListaMedicosResponse;
import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.response.MotivosaHabeas;
import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.response.SyncResponse;
import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.services.HabeasService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/habeas")
@RequiredArgsConstructor
public class HabeasController {

    private final HabeasService habeasService;

    @GetMapping("/existe")
    public ResponseEntity<Map<String, Object>> existeHabeas(
            @RequestParam("noIdentificacion") String noIdentificacion,
            @RequestParam("tipoId") String tipoId) {
        log.debug("Entró a GET /habeas/existe con noIdentificacion={} y tipoId={}", noIdentificacion, tipoId);

        try {
            List<HabeasResponse> resultado = habeasService.buscarHabeas(noIdentificacion, tipoId);

            Map<String, Object> body = new HashMap<>();

            if (!resultado.isEmpty()) {
                body.put("code", 200);
                body.put("description", "OK");
                body.put("results", resultado);
                return ResponseEntity.ok(body);
            } else {
                body.put("code", 404);
                body.put("description", "No se encontró el paciente con Habeas Data");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
            }

        } catch (Exception ex) {
            log.error("Error al verificar existencia de Habeas Data", ex);
            Map<String, Object> error = new HashMap<>();
            error.put("code", 500);
            error.put("description", "Error interno del servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * POST /habeas/registrar — recibe un JSON con todos los campos de HabeasRequest
     */
    @PostMapping("/registrar")
    public ResponseEntity<SyncResponse> registrarHabeas(@RequestBody HabeasRequest req) {
        try {
            Long idRegistro = habeasService.registrar(req);
            return ResponseEntity.ok(new SyncResponse(idRegistro));
        } catch (Exception ex) {
            log.error("Error al registrar Habeas Data", ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SyncResponse("Error interno"));
        }
    }

    @GetMapping("/secretaria/{idUsuario}/{idPaciente}/{aprobacion}")
    public ResponseEntity<List<ListaMedicosResponse>> obtenerMedicos(
            @PathVariable String idUsuario,
            @PathVariable String idPaciente,
            @PathVariable String aprobacion) {

        List<ListaMedicosResponse> resultado = habeasService.buscarMedicosPorUsuario(idUsuario, idPaciente, aprobacion);

        if (resultado.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }

        return ResponseEntity.ok(resultado); // 200 OK con lista de médicos
    }

    @GetMapping("/motivos")
    public ResponseEntity<List<MotivosaHabeas>> listarMotivosHabeas() {
        List<MotivosaHabeas> motivos = habeasService.cargarListaMotivosaHabeas();
        if (motivos.isEmpty()) {
            // 204 No Content si no hay ningún motivo
            return ResponseEntity.noContent().build();
        }
        // 200 OK con la lista de motivos
        return ResponseEntity.ok(motivos);
    }

    @PostMapping("/enviar")
    public ResponseEntity<String> enviarSMS(@RequestBody mensajeRequest mensaje) {
        return habeasService.reenviarSms(mensaje);
    }

    @PostMapping("/validarCodigo")
    public ResponseEntity<Map<String, Object>> validarCodigo(@RequestBody Map<String, String> request) {
        String noIdentificacion = request.get("noIdentificacion");
        String codigo = request.get("codigo");

        boolean actualizado = habeasService.validarYActualizar(noIdentificacion, codigo);

        Map<String, Object> response = new HashMap<>();
        response.put("success", actualizado);
        response.put("message", actualizado
                ? "Código validado y actualizado"
                : "Código no válido o ya aprobado");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/enviarCorreo")
    public ResponseEntity<String> enviarCorreo(@RequestBody mensajeRequest mensaje) {
        // Dispara la tarea asíncrona
        habeasService.enviarCorreoAsync(mensaje);

        // Devuelve inmediatamente una respuesta sin esperar que termine
        return ResponseEntity.ok("🚀 Envío de correo en proceso.");
    }

}
