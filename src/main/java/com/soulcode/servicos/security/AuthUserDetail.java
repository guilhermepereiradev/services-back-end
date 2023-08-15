package com.soulcode.servicos.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//Abstrai o User do banco para que o spring security conheça seus dados
public class AuthUserDetail implements UserDetails {

    private String login;
    private String password;

    public AuthUserDetail(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }// a conta não inspirou

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }// a conta não bloqueou

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    } // as credenciais não expiraram

    @Override
    public boolean isEnabled() {
        return true;
    } // o usuario está habilitado
}