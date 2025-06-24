package com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncResponse {
    private Long id;
    private String message;
    
    public SyncResponse(Long id) {
        this.id = id;
        this.message = "OK";
    }
    
    public SyncResponse(String message) {
        this.message = message;
    }
}