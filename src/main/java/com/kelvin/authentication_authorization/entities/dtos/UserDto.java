package com.kelvin.authentication_authorization.entities.dtos;

import com.kelvin.authentication_authorization.entities.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UserDto {

    private String name;
    private String email;
    private String password;
    private Role role;

    public UserDto(){

    }
    public String getName(){
        return this.name;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPassword(){
        return this.password;
    }
    public Role getRole(){
        return this.role;
    }
}
