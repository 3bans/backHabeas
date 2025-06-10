package com.interopDllo.authServer.services;

import com.interopDllo.authServer.dto.TokenDto;
import com.interopDllo.authServer.dto.UserDto;


public interface AuthService {


    TokenDto login(UserDto user);
    TokenDto validateToken(TokenDto token);

}
