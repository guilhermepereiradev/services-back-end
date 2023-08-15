package com.soulcode.servicos.service;

import com.soulcode.servicos.model.User;
import com.soulcode.servicos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Cacheable("userCache")
    public List<User> listarUsers(){
        return userRepository.findAll();
    }

    @CachePut(value = "userCache", key = "#user.id")
    public User cadastrasUser(User user){
        return userRepository.save(user);
    }
}
