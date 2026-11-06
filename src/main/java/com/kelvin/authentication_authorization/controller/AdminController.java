package com.kelvin.authentication_authorization.controller;

import com.kelvin.authentication_authorization.entities.dtos.RegisterUserDto;
import com.kelvin.authentication_authorization.entities.Users;
import com.kelvin.authentication_authorization.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AuthService authService;

    public AdminController(AuthService authService){
        this.authService=authService;
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Users> createAdmin(@RequestBody RegisterUserDto registerUserDto){
        Users newAdmin= authService.createAdmin(registerUserDto);

        return ResponseEntity.ok(newAdmin);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Users> assignAdminRole(@PathVariable UUID id){
        Users updatedUser= authService.AssignAdminRole(id);

        return ResponseEntity.ok(updatedUser);
    }
}
