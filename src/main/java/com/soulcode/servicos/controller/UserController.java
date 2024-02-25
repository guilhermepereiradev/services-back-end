package com.soulcode.servicos.controller;

import com.soulcode.servicos.model.User;
import com.soulcode.servicos.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/servicos")
public class UserController{

    private final UserService userService;


    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/usuarios")
    public List<User> listarUsuarios(){
        return userService.listarUsers();
    }

    @PostMapping("/usuarios")
    public ResponseEntity<User> cadastrasUser(@RequestBody User user){
        String senhaCodificada = passwordEncoder.encode(user.getPassword());
        user.setPassword(senhaCodificada);
        user = userService.cadastrasUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
