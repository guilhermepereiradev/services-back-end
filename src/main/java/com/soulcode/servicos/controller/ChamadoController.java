package com.soulcode.servicos.controller;

import com.soulcode.servicos.model.Chamado;
import com.soulcode.servicos.model.StatusChamado;
import com.soulcode.servicos.service.ChamadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/servicos/chamados")
public class ChamadoController {

    private final ChamadoService chamadoService;

    public ChamadoController(ChamadoService chamadoService) {
        this.chamadoService = chamadoService;
    }

    @GetMapping
    public ResponseEntity<List<Chamado>> buscar(){
        return ResponseEntity.ok(chamadoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chamado> mostrarChamadoPeloId(@PathVariable Integer id){
        return ResponseEntity.ok(chamadoService.buscarOuFalhar(id));
    }

    @GetMapping("/{idCliente}/cliente")
    public ResponseEntity<List<Chamado>> buscarChamadosPeloCliente(@PathVariable Integer idCliente){
        return ResponseEntity.ok(chamadoService.bucarPeloCliente(idCliente));
    }

    @GetMapping("/{idFuncionario}/funcionario")
    public ResponseEntity<List<Chamado>> buscarChamadoPeloFuncionario(@PathVariable Integer idFuncionario){
        return ResponseEntity.ok(chamadoService.buscarChamadoPeloFuncionario(idFuncionario));
    }

    @GetMapping("/status")
    public ResponseEntity<List<Chamado>> buscarChamadoPeloStatus(@RequestParam("status") StatusChamado status){
        return ResponseEntity.ok(chamadoService.buscarChamadoPeloStatus(status));
    }

    @GetMapping("/chamadosPorIntervaloData")
    public ResponseEntity<List<Chamado>> buscarCbuscarChamadoPorIntervaloData(
                                    @RequestParam("data1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data1,
                                    @RequestParam("data2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)Date data2) {
        return ResponseEntity.ok(chamadoService.buscarChamadoPorIntervaloData(data1, data2));
    }

    @PostMapping("/chamados/{idCliente}/clientes")
    public ResponseEntity<Chamado> cadastrarChamado(@RequestBody Chamado chamado, @PathVariable Integer idCliente){
        chamado = chamadoService.cadastrarChamado(chamado, idCliente);
        URI novaUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(chamado.getId()).toUri();
        return ResponseEntity.created(novaUri).body(chamado);
    }

    @DeleteMapping("/chamados/{idChamado}")
    public ResponseEntity<Void> excluirChamado(@PathVariable Integer idChamado){
        chamadoService.excluirChamado(idChamado);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/chamados/{idChamado}")
    public ResponseEntity<Chamado> editarChamado(@RequestBody Chamado chamado, @PathVariable Integer idChamado){
        Chamado oldChamado = chamadoService.buscarOuFalhar(idChamado);
        BeanUtils.copyProperties(chamado, oldChamado);
        chamadoService.cadastrarChamado(chamado, idChamado);
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
