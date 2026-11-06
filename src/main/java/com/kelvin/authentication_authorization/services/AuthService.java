package com.kelvin.authentication_authorization.services;

import com.kelvin.authentication_authorization.entities.dtos.LoginRequestDto;
import com.kelvin.authentication_authorization.entities.dtos.RegisterUserDto;
import com.kelvin.authentication_authorization.entities.Role;
import com.kelvin.authentication_authorization.entities.RoleEnum;
import com.kelvin.authentication_authorization.entities.Users;
import com.kelvin.authentication_authorization.repository.RoleRepository;
import com.kelvin.authentication_authorization.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager){
        this.roleRepository=roleRepository;
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.authenticationManager=authenticationManager;

    }

    public Users registerUser(RegisterUserDto newuser){
        if(userRepository.existsByEmail(newuser.getEmail())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Emails exists");
        }

        Optional<Role> optionalRole=roleRepository.findRoleByName(RoleEnum.USER);

        if(optionalRole.isEmpty()){
            return null;
        }

        Users user= new Users();
        user.setFullname(newuser.getName());
        user.setEmail(newuser.getEmail());
        user.setPassword(passwordEncoder.encode(newuser.getPassword()));
        user.setRole(optionalRole.get());

        return userRepository.save(user);

    }

    public Users createAdmin(RegisterUserDto createAdmin){
        Optional<Role> optionalRole=roleRepository.findRoleByName(RoleEnum.ADMIN);

        if(optionalRole.isEmpty()){
            return null;
        }

        var user= new Users();
        user.setFullname(createAdmin.getName());
        user.setEmail(createAdmin.getEmail());
        user.setPassword(passwordEncoder.encode(createAdmin.getPassword()));
        user.setRole(optionalRole.get());

        return userRepository.save(user);
    }

    public Users loginUser(LoginRequestDto loginRequestDto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );
        return userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow();
    }

    public Users AssignAdminRole(UUID id){
        Users existingUser= userRepository.findById(id).orElseThrow(()->
                new IllegalStateException(id + " not found"));
        Optional<Role> optionalRole=roleRepository.findRoleByName(RoleEnum.ADMIN);

        if(optionalRole.isEmpty()){
            return null;
        }

        existingUser.setRole(optionalRole.get());

        return userRepository.save(existingUser);

    }
}
