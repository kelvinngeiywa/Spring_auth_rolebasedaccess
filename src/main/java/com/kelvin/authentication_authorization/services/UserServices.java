package com.kelvin.authentication_authorization.services;

import com.kelvin.authentication_authorization.entities.dtos.UserDto;
import com.kelvin.authentication_authorization.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {
    private final UserRepository userRepository;

    public UserServices(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public List<UserDto> getAllUser(){
        return userRepository.findAll().stream().map(u->new UserDto()).toList();
    }
}
