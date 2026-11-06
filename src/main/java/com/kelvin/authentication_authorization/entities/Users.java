package com.kelvin.authentication_authorization.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "id",columnDefinition = "CHAR(36)",nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    @Column(nullable = false)
    private String fullname;
    @Column(nullable = false)
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;
    @Column(nullable = false)
    @NotNull(message = "Password cannot be null")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,72}$",
            message = "Password must be at least 8 characters long, contain at least one digit, one uppercase, one lowercase, and one special character."
    )
    private String password;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "role_id",referencedColumnName = "id",nullable = false)
    private Role role;

    public Users(){

    }

    public Users(UUID id,String fullname,String email,String password,Role role){
        this.id=id;
        this.fullname=fullname;
        this.email=email;
        this.password=password;
        this.role=role;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        SimpleGrantedAuthority authority=new SimpleGrantedAuthority("ROLE_"+ role.getName().toString());
        return List.of(authority);
    }
    @Override
    public String getUsername(){
        return this.email;
    }

    public UUID getId(){
        return this.id;
    }
    public String getFullname(){
        return this.fullname;
    }

    public void setFullname(String fullname){
        this.fullname=fullname;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public Role getRole(){
        return this.role;
    }

    public Role setRole(Role role){
        return this.role=role;

    }

}
