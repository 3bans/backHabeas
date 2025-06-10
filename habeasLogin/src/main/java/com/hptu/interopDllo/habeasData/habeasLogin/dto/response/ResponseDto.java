package com.hptu.interopDllo.habeasData.habeasLogin.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    private int code;
    private String description;
}