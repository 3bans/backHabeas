package com.interopDllo.authServer.services.implement;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.interopDllo.authServer.dto.TokenDto;
import com.interopDllo.authServer.dto.UserDto;
import com.interopDllo.authServer.repository.UserRepository;
import com.interopDllo.authServer.services.AuthService;
import com.interopDllo.authServer.utils.helpers.JwtHelper;



@Transactional
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{
    

    private final UserRepository userRepository;
    private final    JwtHelper jwtHelper;
     private  static final String USER_EXCEPTION_MSG="Error to auth user";

    @Override

    public TokenDto login(UserDto user) {
      final var userFromDB = this.userRepository.findByIdUsuario(user.getIdUsuario())
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,USER_EXCEPTION_MSG));
   
      
      return TokenDto.builder().accessToken(this.jwtHelper.createToken(userFromDB.getIdUsuario())).build();
    }

    @Override
    public TokenDto validateToken(TokenDto token) {
          
        if (this.jwtHelper.validateToken(token.getAccessToken())){
             return    TokenDto.builder().accessToken(token.getAccessToken()).build();   
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,USER_EXCEPTION_MSG);
    }


 

}
