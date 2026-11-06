package com.kelvin.authentication_authorization.entities.dtos;

public class LoginRequestDto {
    private String email;
    private String password;

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }
}
