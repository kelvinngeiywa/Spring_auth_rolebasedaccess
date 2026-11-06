package com.kelvin.authentication_authorization.controller;

import com.kelvin.authentication_authorization.entities.dtos.UserDto;
import com.kelvin.authentication_authorization.services.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserServices userServices;

    public UserController(UserServices userServices){
        this.userServices=userServices;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users=userServices.getAllUser();
        return ResponseEntity.ok(users);

    }
}
