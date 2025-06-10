package com.hptu.interopDllo.habeasData.habeasLogin.dto.response;

import lombok.Data;

@Data
public class LoginUser {
    private String code;
    private String description;
    private String fullName;
    private String grups;
    private String cargo;
    private boolean flag;
}
