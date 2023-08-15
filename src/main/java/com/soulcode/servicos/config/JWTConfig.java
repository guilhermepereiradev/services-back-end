package com.soulcode.servicos.config;

import com.soulcode.servicos.security.JWTAuthenticationFilter;
import com.soulcode.servicos.security.JWTAuthorizationFilter;
import com.soulcode.servicos.service.AuthUserDetailService;
import com.soulcode.servicos.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

//Agrega todas as informações de segurança http, e gerencia do user
@EnableWebSecurity
public class JWTConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthUserDetailService authUserDetailService;//carrega o usuario do banco

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // UserDetailService -> carregar o usuario do banco
        // passwordEncoder(BCrypt) -> gerador de hash de senhas
        //Usa passwordEnconder() para comparar senhas de login
        auth.userDetailsService(authUserDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // habilita o cors e desabilita o csrf(proteção contra o ataque csrf)
        http.cors().and().csrf().disable();
        // JWTAuthenticatosFilter é chamado quando uso o /login
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtils));
        //
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtils));

        http.authorizeRequests().antMatchers(HttpMethod.POST, "/login").permitAll()
                //.antMatchers(HttpMethod.GET, "/servicos/**").permitAll() // libera GET para /servicos/qualquerCoisa
                .anyRequest().authenticated();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Bean //Cors = Cross Origin resource sharing //permite que um caminho diferente possa realizar os metodos http
    CorsConfigurationSource corsConfigurationSource() { // configuração global de CORS
        CorsConfiguration configuration = new CorsConfiguration(); //configurações padrões
        configuration.setAllowedMethods(List.of( //metodos permitidos para o front acessar
                HttpMethod.GET.name(),
                HttpMethod.PUT.name(),
                HttpMethod.POST.name(),
                HttpMethod.DELETE.name()
        ));
        //identifica quais endpoints podem ser acessados // /** => todos os endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //endpoints permitidos para o front acessar
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        return source; //retornar pra o spring security
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
