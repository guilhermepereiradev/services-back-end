package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.Endereco;
import com.soulcode.Servicos.Services.ClienteService;
import com.soulcode.Servicos.Services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/servicos")
public class EnderecoController {

    @Autowired
    EnderecoService enderecoService;
    @Autowired
    ClienteService clienteService;


    @GetMapping("/enderecos")
    public List<Endereco> mostrarEnderecos(){
        List<Endereco> enderecos = enderecoService.mostrarEnderecos();
        return enderecos;
    }

    @GetMapping("/endereco/{idEndereco}")
    public ResponseEntity<Endereco> mostrarEnderecoPeloId(@PathVariable Integer idEndereco){
        Endereco endereco = enderecoService.mostrarEnderecoPeloId(idEndereco);
        return  ResponseEntity.ok().body(endereco);
    }

    @GetMapping("/enderecoPeloCliente/{idCliente}")
    public ResponseEntity<Endereco> mostrarEnderecoPeloCliente(@PathVariable Integer idCliente){
        Endereco endereco = enderecoService.mostrarEnderecoPeloCliente(idCliente);
        return ResponseEntity.ok().body(endereco);
    }

    @PostMapping("/endereco/{idCliente}")
    public ResponseEntity<Endereco> cadastrarEndereco(@PathVariable Integer idCliente, @RequestBody Endereco endereco){
        endereco = enderecoService.cadastrarEndereco(endereco, idCliente);
        URI novaURI = ServletUriComponentsBuilder.fromCurrentRequest().path("id").buildAndExpand(endereco.getIdEndereco()).toUri();
        return ResponseEntity.created(novaURI).body(endereco);
    }

    @PutMapping("/enderecos/{idCliente}")
    public ResponseEntity<Endereco> atualiazarEndereco(@PathVariable Integer idCliente, @RequestBody Endereco endereco){
        endereco = enderecoService.atualiazarEndereco(idCliente, endereco);
        return ResponseEntity.ok().body(endereco);
    }

    @DeleteMapping("/enderecoPeloCliente/{idCliente}")
    public ResponseEntity<Void> excluirEnderecoPeloCliente(@PathVariable Integer idCliente){
        enderecoService.excluirEnderecoPeloCliente(idCliente);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/endereco/{idEndereco}")
    public ResponseEntity<Void> excluirEnderecoPeloId(@PathVariable Integer idEndereco){
        enderecoService.excluirEndereco(idEndereco);
        return ResponseEntity.noContent().build();
    }
}
