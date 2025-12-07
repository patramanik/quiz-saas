package com.quizsaas.config;

import com.quizsaas.security.ApiKeyFilter;
import com.quizsaas.security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private ApiKeyFilter apiKeyFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // PUBLIC
                        .requestMatchers("/auth/login", "/auth/register").permitAll()

                        // SUPER ADMIN
                        .requestMatchers("/super-admin/**").hasRole("SUPER_ADMIN")

                        // ORG ADMIN
                        .requestMatchers("/org-admin/**").hasAnyRole("ORG_ADMIN", "SUPER_ADMIN")

                        // TEACHER
                        .requestMatchers("/teacher/**").hasAnyRole("TEACHER", "ORG_ADMIN", "SUPER_ADMIN")

                        // STUDENT
                        .requestMatchers("/student/**").hasAnyRole("STUDENT", "TEACHER", "ORG_ADMIN", "SUPER_ADMIN")

                        // API Key Secure Routes
                        .requestMatchers("/api/v1/**").authenticated()

                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(apiKeyFilter, JwtAuthFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
