package com.soulcode.servicos.controller;

import com.soulcode.servicos.dtos.ChamadoResumoResponse;
import com.soulcode.servicos.dtos.ClienteResponse;
import com.soulcode.servicos.dtos.ClienteResumoResponse;
import com.soulcode.servicos.model.Cliente;
import com.soulcode.servicos.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/servicos/clientes")
public class ClienteController {

    final
    ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * TODO
     *  Implementar consulta com filtro por email
     */
    @GetMapping
    public ResponseEntity<List<ClienteResumoResponse>> mostrarTodosClientes(){
        var clientes = clienteService.listar()
                .stream()
                .map(ClienteResumoResponse::new)
                .toList();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> mostrarClientePeloId(@PathVariable Integer id){
        var cliente = clienteService.buscarOuFalhar(id);
        var chamados = cliente.getChamados().stream().map(ChamadoResumoResponse::new).toList();
        return ResponseEntity.ok(new ClienteResponse(cliente, chamados));
    }


    @PostMapping("/clientes")
    public ResponseEntity<ClienteResponse> cadastrarCliente(@RequestBody Cliente cliente){
        cliente = clienteService.cadastrarCliente(cliente);
        var chamados = cliente.getChamados().stream().map(ChamadoResumoResponse::new).toList();
        URI novaUri = ServletUriComponentsBuilder.fromCurrentRequest().path("id").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(novaUri).body(new ClienteResponse(cliente, chamados));
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Integer id){
        clienteService.excluirCliente(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/clientes/{idCliente}")
    public ResponseEntity<ClienteResponse> editarCliente(@PathVariable Integer idCliente, @RequestBody Cliente cliente){
        cliente.setId(idCliente);
        clienteService.editarCliente(cliente);
        return ResponseEntity.ok().build();
    }
}
