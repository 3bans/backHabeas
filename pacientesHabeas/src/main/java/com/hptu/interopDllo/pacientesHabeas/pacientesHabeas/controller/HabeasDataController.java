package com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;          // <-- import correcto

import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.request.ConsentRequest;
import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.services.HabeasDataTextService;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/habeasText")
@RequiredArgsConstructor
public class HabeasDataController {
private final HabeasDataTextService service;

    /** GET /habeasText/{id} -> devuelve la plantilla cruda. */
    @GetMapping(value = "/{id}", produces = "text/plain")
    public ResponseEntity<String> getTemplate(@PathVariable Long id) {
        return ResponseEntity.ok(service.getTemplateById(id));
    }

    /** 
     * POST /habeasText/{id}/render 
     * Rellena la plantilla y devuelve el texto completo.
     */
    @PostMapping(value = "/{id}/render", 
                 consumes = "application/json", 
                 produces = "text/plain")
    public ResponseEntity<String> renderConsent(
            @PathVariable Long id,
            @RequestBody ConsentRequest request) {    // <-- usa este @RequestBody
        String fullText = service.renderConsent(id, request);
        return ResponseEntity.ok(fullText);
    }
}
