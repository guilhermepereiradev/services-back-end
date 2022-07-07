package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Models.Chamado;
import com.soulcode.Servicos.Services.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.*;
import java.net.URI;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("servicos")
public class ChamadoController {

    @Autowired
    ChamadoService chamadoService;

    @GetMapping("/chamados")
    public List<Chamado> mostrarChamados(){
        List<Chamado> chamados = chamadoService.mostrarChamados();
        return chamados;
    }

    @GetMapping("/chamados/{idChamado}")
    public ResponseEntity<Chamado> mostrarChamadoPeloId(@PathVariable Integer idChamado){
        Chamado chamado = chamadoService.mostrarChamadoPeloId(idChamado);
        return ResponseEntity.ok().body(chamado);
    }

    @GetMapping("/chamadosPeloCliente/{idCliente}")
    public List<Chamado> buscarChamadosPeloCliente(@PathVariable Integer idCliente){
        List<Chamado> chamados = chamadoService.buscarChamadosPeloCliente(idCliente);
        return chamados;
    }

    @GetMapping("/chamadosPeloFuncionario/{idFuncionario}")
    public List<Chamado> buscarChamadoPeloFuncionario(@PathVariable Integer idFuncionario){
        List<Chamado> chamados = chamadoService.buscarChamadoPeloFuncionario(idFuncionario);
        return chamados;
    }

    @GetMapping("/chamadosPeloStatus")
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
