package com.soulcode.servicos.security;

import com.soulcode.servicos.util.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private JWTUtils jwtUtils;

    public JWTAuthorizationFilter(AuthenticationManager manager, JWTUtils jwtUtils){
        super(manager);
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("Authorization"); //Bearer <token>
        if(token != null && token.startsWith("Bearer")){ // token "válido"
          UsernamePasswordAuthenticationToken authToken = getAuthentication(token.substring(7));
          if (authToken != null){
              SecurityContextHolder.getContext().setAuthentication(authToken);
          }
        }
        chain.doFilter(request, response);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token){
        String login = jwtUtils.getLogin(token);
        if(login == null){
            return null;
        }
        return new UsernamePasswordAuthenticationToken(login, null, new ArrayList<>());
    }
}
