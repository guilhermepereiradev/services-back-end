package com.soulcode.Servicos.Config;

import com.soulcode.Servicos.Security.JWTAuthenticationFilter;
import com.soulcode.Servicos.Security.JWTAuthorizationFilter;
import com.soulcode.Servicos.Services.AuthUserDetailService;
import com.soulcode.Servicos.Util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class JWTConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthUserDetailService authUserDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authUserDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtils));
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtils));

        http.authorizeRequests().antMatchers(HttpMethod.POST, "/login").permitAll()
                //.antMatchers(HttpMethod.GET, "/servicos/**").permitAll() // libera GET para /servicos/qualquerCoisa
                .anyRequest().authenticated();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
