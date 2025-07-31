package com.alpctr.digitalwalletsystem.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable for simplicity in testing
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
            )
            .httpBasic(Customizer.withDefaults())
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin()) // allow H2 console frames from same origin
            );

        return http.build();
    }


    @Bean
    public UserDetailsService users() {
        UserDetails employee = User.builder()
                .username("00000000000") // Employee TCKN
                .password(passwordEncoder().encode("password"))
                .roles("EMPLOYEE")
                .build();

        UserDetails customer = User.builder()
                .username("56789012345") // Customer TCKN
                .password(passwordEncoder().encode("password"))
                .roles("CUSTOMER")
                .build();

        return new InMemoryUserDetailsManager(employee, customer);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
