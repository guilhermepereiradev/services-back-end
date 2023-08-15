package com.soulcode.servicos.controller;

import com.soulcode.servicos.model.Chamado;
import com.soulcode.servicos.service.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/servicos/chamados")
public class ChamadoController {

    @Autowired
    ChamadoService chamadoService;

    @GetMapping
    public ResponseEntity<List<Chamado>> buscar(){
        return ResponseEntity.ok(chamadoService.mostrarChamados());
    }

    @GetMapping("/{idChamado}")
    public ResponseEntity<Chamado> mostrarChamadoPeloId(@PathVariable Integer idChamado){
        Chamado chamado = chamadoService.mostrarChamadoPeloId(idChamado);
        return ResponseEntity.ok().body(chamado);
    }

    @GetMapping("/{idCliente}/cliente")
    public List<Chamado> buscarChamadosPeloCliente(@PathVariable Integer idCliente){
        return chamadoService.buscarChamadosPeloCliente(idCliente);
    }

    @GetMapping("/{idFuncionario}/funcionario")
    public List<Chamado> buscarChamadoPeloFuncionario(@PathVariable Integer idFuncionario){
        List<Chamado> chamados = chamadoService.buscarChamadoPeloFuncionario(idFuncionario);
        return chamados;
    }

    @GetMapping("/status")
    public List<Chamado> buscarChamadoPeloStatus(@RequestParam("status") String status){
        List<Chamado> chamados = chamadoService.buscarChamadoPeloStatus(status);
        return chamados;
    }

    @GetMapping("/chamadosPorIntervaloData")
    public List<Chamado> buscarCbuscarChamadoPorIntervaloData(@RequestParam("data1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data1, @RequestParam("data2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)Date data2){
        List<Chamado> chamados = chamadoService.buscarChamadoPorIntervaloData(data1, data2);
        return chamados;
    }

    @PostMapping("/chamados/{idCliente}")
    public ResponseEntity<Chamado> cadastrarChamado(@RequestBody  Chamado chamado, @PathVariable Integer idCliente){
        chamado = chamadoService.cadastrarChamado(chamado, idCliente);
        URI novaUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(chamado.getIdChamado()).toUri();
        return ResponseEntity.created(novaUri).body(chamado);
    }

    @DeleteMapping("/chamados/{idChamado}")
    public ResponseEntity<Void> excluirChamado(@PathVariable Integer idChamado){
        chamadoService.excluirChamado(idChamado);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/chamados/{idChamado}")
    public ResponseEntity<Chamado> editarChamado(@RequestBody Chamado chamado, @PathVariable Integer idChamado){
        chamado.setIdChamado(idChamado);
        chamadoService.editarChamado(chamado, idChamado);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/chamadosAtribuirFuncionario/{idChamado}/{idFuncionario}")
    public ResponseEntity<Chamado> atribuirFuncionario(@PathVariable Integer idChamado, @PathVariable Integer idFuncionario){
        chamadoService.atribuirFuncionario(idChamado, idFuncionario);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("chamadosModificarStatus/{idChamado}")
    public ResponseEntity<Chamado> modificarStatus(@PathVariable Integer idChamado, @RequestParam String status){
        chamadoService.modificarStatus(idChamado, status);
        return ResponseEntity.noContent().build();
    }
}
