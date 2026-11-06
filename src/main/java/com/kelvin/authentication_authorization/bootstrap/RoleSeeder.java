package com.kelvin.authentication_authorization.bootstrap;

import com.kelvin.authentication_authorization.entities.Role;
import com.kelvin.authentication_authorization.entities.RoleEnum;
import com.kelvin.authentication_authorization.repository.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository){
        this.roleRepository=roleRepository;
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent){
        this.loadRoles();
    }

    private void loadRoles(){
        RoleEnum[] roleNames=new RoleEnum[] {RoleEnum.USER,RoleEnum.ADMIN,RoleEnum.SUPER_ADMIN};
        Map<RoleEnum,String> roleEnumStringMap=Map.of(
                RoleEnum.USER,"Default user role",
                RoleEnum.ADMIN,"Administrator role",
                RoleEnum.SUPER_ADMIN,"Super administrator role"
        );

        Arrays.stream(roleNames).forEach((roleName)->{
            Optional<Role> role = roleRepository.findRoleByName(roleName);

            role.ifPresentOrElse(System.out::println,()->{
                Role createRole= new Role();



                createRole.setName(roleName);
                createRole.setDescription(roleEnumStringMap.get(roleName));

                roleRepository.save(createRole);

            });


        });
    }
}
