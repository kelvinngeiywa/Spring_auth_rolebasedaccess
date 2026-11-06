package com.kelvin.authentication_authorization.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigs {
    private final TokenAuthenticationEntryPoint tokenAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfigs(TokenAuthenticationEntryPoint tokenAuthenticationEntryPoint,
                           CustomAccessDeniedHandler accessDeniedHandler,
                           AuthenticationProvider authenticationProvider,
                           JwtAuthenticationFilter jwtAuthenticationFilter){
        this.tokenAuthenticationEntryPoint=tokenAuthenticationEntryPoint;
        this.accessDeniedHandler=accessDeniedHandler;
        this.authenticationProvider=authenticationProvider;
        this.jwtAuthenticationFilter=jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(c->c.disable())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/auth/register","/auth/login","/error").permitAll()
                        .requestMatchers("/admin").hasRole("SUPER_ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e->e
                        .authenticationEntryPoint(tokenAuthenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .build();

    }
}
