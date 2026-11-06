package com.kelvin.authentication_authorization.repository;

import com.kelvin.authentication_authorization.entities.Role;
import com.kelvin.authentication_authorization.entities.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findRoleByName(RoleEnum Name);
}
