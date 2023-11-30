package com.backend.service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests(auth -> auth.requestMatchers(antMatcher("/auth/**")).permitAll())
                // allow all request under /auth/* to be permitted without spring authentication
                .authorizeHttpRequests(auth -> auth.requestMatchers(antMatcher("/certificates/**")).permitAll())
                .build();

    }


}
