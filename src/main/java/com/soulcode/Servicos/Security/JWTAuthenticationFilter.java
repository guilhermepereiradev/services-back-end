package com.soulcode.Servicos.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soulcode.Servicos.Models.User;
import com.soulcode.Servicos.Util.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JWTUtils jwtUtils;

    public JWTAuthenticationFilter(AuthenticationManager manager, JWTUtils jwtUtils){
        this.authenticationManager = manager;
        this.jwtUtils = jwtUtils;
    }

    //

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            //transfotmar o json de request em um user
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword(), new ArrayList<>()));
        }catch (IOException io){
            throw new RuntimeException(io.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        AuthUserDetail user = (AuthUserDetail) authResult.getPrincipal();
        String token = jwtUtils.generationToken(user.getUsername());

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, PATCH, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        // {"Authorization: "<token>"}
        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
        response.getWriter().flush();
    }
}
/*
* Front manda {"login": "123", "password": ""123}
* A partir do JSON -> User
* Tenta realizar a autenticação
*   caso de certo:
*       -gera o token JWT
*       - retorna o token para o Front
* */