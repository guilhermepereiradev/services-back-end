package com.soulcode.servicos.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soulcode.servicos.model.User;
import com.soulcode.servicos.util.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

//essa classe entra em ação ao chamar /login
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JWTUtils jwtUtils;

    public JWTAuthenticationFilter(AuthenticationManager manager, JWTUtils jwtUtils){
        this.authenticationManager = manager;
        this.jwtUtils = jwtUtils;
    }

    //


    //tenta autenticar o usuario
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            //transfotmar o json de {request} em um user
            //extrair informações de user da request "bruta"
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate( //chama a autenticacao do spring
                    new UsernamePasswordAuthenticationToken( //informações do user e permissões
                            user.getLogin(),
                            user.getPassword(),
                            new ArrayList<>()));
        }catch (IOException io){
            // caso o json da requisição não bater com o user.class
            throw new RuntimeException(io.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // gerar o token e devolver para o usuario que se autenticou com sucesso
        AuthUserDetail user = (AuthUserDetail) authResult.getPrincipal(); // retorna o user
        String token = jwtUtils.generationToken(user.getUsername()); // retorna o token

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, PATCH, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        // {"Authorization: "<token>"}
        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401); //unauthorized
        response.setContentType("aplication/json"); // tipo da resposta
        response.getWriter().write(json()); // mensagem de erro no body
        response.getWriter().flush(); // termina a escrita
    }

    String json(){ //formatar a msg de erro
        long date = new Date().getTime();
        return "{\"timestamp\": "+ date +", \"status\": 401, \"error\": \"Não autorizado\", \"message\": \"Email/senha inválidos\", \"path\": \"/login\"}";
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