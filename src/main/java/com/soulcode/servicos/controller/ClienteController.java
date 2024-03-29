package com.soulcode.servicos.controller;

import com.soulcode.servicos.dtos.ChamadoResumoResponse;
import com.soulcode.servicos.dtos.ClienteRequest;
import com.soulcode.servicos.dtos.ClienteResponse;
import com.soulcode.servicos.dtos.ClienteResumoResponse;
import com.soulcode.servicos.model.Cliente;
import com.soulcode.servicos.service.ClienteService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/servicos/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteResumoResponse>> mostrarTodosClientes(){
        var clientes = clienteService.listar()
                .stream()
                .map(ClienteResumoResponse::new)
                .toList();
        return ResponseEntity.ok(clientes);
    }
    @GetMapping(params = "email")
    public ResponseEntity<ClienteResponse> buscarClientesPorEmail(@RequestParam(required = false) String email){
        var cliente = clienteService.buscarClientePeloEmail(email);
        var chamados = cliente.getChamados().stream().map(ChamadoResumoResponse::new).toList();
        return ResponseEntity.ok(new ClienteResponse(cliente, chamados));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> mostrarClientePeloId(@PathVariable Integer id){
        var cliente = clienteService.buscarOuFalhar(id);
        var chamados = cliente.getChamados().stream().map(ChamadoResumoResponse::new).toList();
        return ResponseEntity.ok(new ClienteResponse(cliente, chamados));
    }


    @PostMapping
    public ResponseEntity<ClienteResumoResponse> cadastrarCliente(@RequestBody ClienteRequest clienteRequest){
        var cliente = new Cliente();
        BeanUtils.copyProperties(clienteRequest, cliente);

        cliente = clienteService.cadastrarCliente(cliente);

        URI novaUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("id")
                .buildAndExpand(cliente.getId())
                .toUri();

        return ResponseEntity.created(novaUri).body(new ClienteResumoResponse(cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Integer id){
        clienteService.excluirCliente(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> editarCliente(@PathVariable Integer id, @RequestBody ClienteRequest clienteRequest){
        var cliente = clienteService.buscarOuFalhar(id);
        BeanUtils.copyProperties(clienteRequest, cliente);

        cliente = clienteService.cadastrarCliente(cliente);
        var chamados = cliente.getChamados()
                .stream()
                .map(ChamadoResumoResponse::new)
                .toList();

        return ResponseEntity.ok(new ClienteResponse(cliente, chamados));
    }
}
