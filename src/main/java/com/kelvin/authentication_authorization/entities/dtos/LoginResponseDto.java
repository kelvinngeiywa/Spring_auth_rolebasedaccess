package com.kelvin.authentication_authorization.entities.dtos;

public class LoginResponseDto {
    private String token;
    private long expiresIn;

    public LoginResponseDto(){

    }

    public LoginResponseDto(String token,long expiresIn){
        this.token=token;
        this.expiresIn=expiresIn;

    }

    public String getToken(){
        return this.token;
    }

    public void setToken(String token){
        this.token=token;
    }

    public long getExpiresIn(){
        return this.expiresIn;
    }
    public void setExpiresIn(long expiresIn){
        this.expiresIn=expiresIn;
    }
}
