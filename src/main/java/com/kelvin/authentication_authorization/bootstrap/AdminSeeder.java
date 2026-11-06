package com.kelvin.authentication_authorization.bootstrap;

import com.kelvin.authentication_authorization.entities.dtos.RegisterUserDto;
import com.kelvin.authentication_authorization.entities.Role;
import com.kelvin.authentication_authorization.entities.RoleEnum;
import com.kelvin.authentication_authorization.entities.Users;
import com.kelvin.authentication_authorization.repository.RoleRepository;
import com.kelvin.authentication_authorization.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public AdminSeeder(UserRepository userRepository, RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
        this.passwordEncoder=passwordEncoder;

    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent){
        this.createSuperAdmin();
    }

    private void createSuperAdmin(){
        RegisterUserDto userDto=new RegisterUserDto();
        userDto.setName("Super Admin");
        userDto.setEmail("super.admin@gmail.com");
        userDto.setPassword("Superadmin@123");

        Optional<Role> role=roleRepository.findRoleByName(RoleEnum.SUPER_ADMIN);
        Optional<Users> user=userRepository.findByEmail(userDto.getEmail());

        if(role.isEmpty() || user.isPresent()){
            return;
        }

        var superuser= new Users();
        superuser.setFullname(userDto.getName());
        superuser.setEmail(userDto.getEmail());
        superuser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        superuser.setRole(role.get());

        userRepository.save(superuser);





    }
}
