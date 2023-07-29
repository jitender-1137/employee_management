package com.universal.em.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//@ConfigurationProperties(prefix = "cors")
//@ConfigurationPropertiesScan
public class SpringSecurityConfig {

    private static final String[] SECURED_URLs = {""};

    private static final String[] UN_SECURED_URLs = {"/api/v1/authenticate/**", "/swagger-ui/**", "/resources/**", "/css/**", "/js/**", "/image/**", "/v3/api-docs/**", "/api/v1/user/save", "/api/v1/**", "/employee/**", "api/v1/logs/download**"};

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authReq -> authReq.requestMatchers(UN_SECURED_URLs).permitAll()).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

//        CorsConfiguration corsConfig = new CorsConfiguration();
//        System.out.println(List.of(allowedOrigins.trim().split("\\s*,\\s*")));
//        corsConfig.setAllowedOrigins(List.of(allowedOrigins.trim().split("\\s*,\\s*")));
//        corsConfig.setAllowedMethods(List.of(allowedMethods.trim().split("\\s*,\\s*")));
//        corsConfig.setAllowedHeaders(List.of(allowedHeaders.trim().split("\\s*,\\s*")));
//        corsConfig.setMaxAge(Long.valueOf(maxAge));
//        http.cors().configurationSource(request -> corsConfig);

        return http.build();
    }

}
