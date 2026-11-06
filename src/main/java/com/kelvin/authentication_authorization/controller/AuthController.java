package com.kelvin.authentication_authorization.controller;

import com.kelvin.authentication_authorization.entities.dtos.LoginRequestDto;
import com.kelvin.authentication_authorization.entities.dtos.LoginResponseDto;
import com.kelvin.authentication_authorization.entities.dtos.RegisterUserDto;
import com.kelvin.authentication_authorization.entities.Users;
import com.kelvin.authentication_authorization.services.AuthService;
import com.kelvin.authentication_authorization.services.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService,JwtService jwtService){

        this.authService=authService;
        this.jwtService=jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@Valid @RequestBody RegisterUserDto newUserDto){
        Users newUser= authService.registerUser(newUserDto);
        return ResponseEntity.ok(newUser);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto loginRequestDto){
        Users loggedUser= authService.loginUser(loginRequestDto);

        String jwtToken=jwtService.generateToken(loggedUser);
        LoginResponseDto loginResponseDto=new LoginResponseDto();
        loginResponseDto.setToken(jwtToken);
        loginResponseDto.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponseDto);
    }
}
