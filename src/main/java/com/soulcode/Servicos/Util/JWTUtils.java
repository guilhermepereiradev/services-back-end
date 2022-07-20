package com.soulcode.Servicos.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils { // gerenciar e gerar tokens
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generationToken(String login){
        return JWT.create().withSubject(login)// login do usuario
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))//tempo de expiração do token
                .sign(Algorithm.HMAC512(secret));
    }

    public String getLogin(String token){
        return JWT.require(Algorithm.HMAC512(secret)).build().verify(token).getSubject();
    }
}
