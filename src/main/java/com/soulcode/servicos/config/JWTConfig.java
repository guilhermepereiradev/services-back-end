package com.soulcode.servicos.config;

import com.soulcode.servicos.security.JWTAuthenticationFilter;
import com.soulcode.servicos.service.AuthUserDetailService;
import com.soulcode.servicos.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class JWTConfig {
    private JWTUtils jwtUtils;

    private AuthUserDetailService authUserDetailService;

    private AuthenticationConfiguration authenticationConfiguration;

    private PasswordEncoder passwordEncoder;

    public JWTConfig(JWTUtils jwtUtils, AuthUserDetailService authUserDetailService, AuthenticationConfiguration authenticationManager, PasswordEncoder passwordEncoder) {
        this.jwtUtils = jwtUtils;
        this.authUserDetailService = authUserDetailService;
        this.authenticationConfiguration = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authUserDetailService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(
                        new JWTAuthenticationFilter(
                                authenticationConfiguration.getAuthenticationManager(), jwtUtils),
                                UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth ->
                        auth
                            .requestMatchers(HttpMethod.POST, "/login")
                                .permitAll()
                            .anyRequest()
                                .authenticated())
                .sessionManagement(session ->
                        session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.PUT.name(),
                HttpMethod.POST.name(),
                HttpMethod.DELETE.name()
        ));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        return source;
    }
}
