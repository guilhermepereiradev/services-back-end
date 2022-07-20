package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.User;
import com.soulcode.Servicos.Repositories.UserRepository;
import com.soulcode.Servicos.Security.AuthUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    //retorna um UserDetails de acordo com o username
    @Cacheable("authUserDetailCache")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(username); //filtro por email
        if(user.isEmpty()){ //ou use "!user.isPresent"
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        //cria um onjeto da clase authUserDetail
        return new AuthUserDetail(user.get().getLogin(), user.get().getPassword());
    }
}

/*
* O proposito do userDetailService é carregar de alguma donte de dados o usuario e criar uma instância de authDetail, conhecida pelo String
* */