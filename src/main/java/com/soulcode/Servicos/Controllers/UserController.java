package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Models.User;
import com.soulcode.Servicos.Repositories.UserRepository;
import com.soulcode.Servicos.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class UserController{

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

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
