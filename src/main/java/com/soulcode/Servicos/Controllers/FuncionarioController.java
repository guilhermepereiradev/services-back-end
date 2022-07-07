package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Services.FuncionarioService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("servicos")
public class FuncionarioController {

    @Autowired
    FuncionarioService funcionarioService;

    @GetMapping("/funcionarios")
    public List<Funcionario> mostrarTodosFuncionarios(){
        List<Funcionario> funcionarios = funcionarioService.mostrarTodosFuncionarios();
        return funcionarios;
    }

    @GetMapping("/funcionarios/{idFuncionario}")
    public ResponseEntity<Funcionario> mostrarUmFuncionarioPeloId(@PathVariable Integer idFuncionario){
        Funcionario funcionario = funcionarioService.mostrarUmFuncionarioPeloId(idFuncionario);
        return ResponseEntity.ok().body(funcionario);
    }

    @GetMapping("/funcionariosEmail/{emailFuncioanario}")
    public ResponseEntity<Funcionario> mostrarUmFuncioanarioPeloEmail(@PathVariable String emailFuncioanario){
        Funcionario funcionario = funcionarioService.mostrarUmFuncioanarioPeloEmail(emailFuncioanario);
        return ResponseEntity.ok().body(funcionario);
    }

    @PostMapping("/funcionarios/{idCargo}")
    public ResponseEntity<Funcionario> cadastrarFuncionario(@RequestBody Funcionario funcionario, @PathVariable Integer idCargo){
//       Na linha 43 o funcionario ja é salvo na tabela
//        agora precisamos criar uma uri para esse novo registro da tabela
        funcionario = funcionarioService.cadastrarFuncionario(funcionario, idCargo);
        URI novaUri = ServletUriComponentsBuilder.fromCurrentRequest().path("id").buildAndExpand(funcionario.getIdFuncionario()).toUri();
        return ResponseEntity.created(novaUri).body(funcionario);
    }

    @DeleteMapping("/funcionarios/{idFuncionario}")
    public ResponseEntity<Void> excluirFuncionario(@PathVariable Integer idFuncionario){
        funcionarioService.excluirFuncionario(idFuncionario);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/funcionarios/{idFuncionario}")
    public ResponseEntity<Funcionario> editarFuncionario(@PathVariable Integer idFuncionario, @RequestBody Funcionario funcionario){
        funcionario.setIdFuncionario(idFuncionario);
        funcionarioService.editarFuncionario(funcionario);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/funcionariosPeloCargo/{idCargo}")
    public List<Funcionario> mostrarFuncionariosPeloCargo(@PathVariable Integer idCargo){
        List<Funcionario> funcionarios = funcionarioService.mostrarFuncionariosPeloCargo(idCargo);
        return funcionarios;
    }
}
