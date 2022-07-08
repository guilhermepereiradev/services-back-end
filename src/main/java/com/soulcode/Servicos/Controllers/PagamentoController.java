package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Models.Pagamento;
import com.soulcode.Servicos.Services.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.print.attribute.standard.PagesPerMinute;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat.URI;

@CrossOrigin
@RestController
@RequestMapping("/servicos")
public class PagamentoController {

    @Autowired
    PagamentoService pagamentoService;

    @GetMapping("/pagamentos")
    public List<Pagamento> mostrarTodosPagamentos(){
        List<Pagamento> pagamentos = pagamentoService.mostrarTodosPagamentos();
        return pagamentos;
    }

    @GetMapping("/pagamentos/{idPagamento}")
    public ResponseEntity<Pagamento> mostrarPagamentoPeloId(@PathVariable Integer idPagamento){
        Pagamento pagamento = pagamentoService.mostrarPagamentoPeloId(idPagamento);
        return ResponseEntity.ok().body(pagamento);
    }

    @GetMapping("/pagamentosPeloStatus")
    public List<Pagamento> mostrarPagamentosPeloStatus(@RequestParam("status") String status){
        List<Pagamento> pagamentos = pagamentoService.mostrarPagamentosPeloStatus(status);
        return pagamentos;
    }

    @PostMapping("/pagamentos/{idChamado}")
    public ResponseEntity<Pagamento> cadastrarPagamento(@PathVariable Integer idChamado,
                                                        @RequestBody Pagamento pagamento){
        pagamento = pagamentoService.cadastrarPagamento(pagamento,idChamado);
        URI novaUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(pagamento.getIdPagamento()).toUri();
        return ResponseEntity.created(novaUri).build();
    }

    @PutMapping("/pagamentos/{idPagamento}")
    public ResponseEntity<Pagamento> editarPagamento(@PathVariable Integer idPagamento,
                                                     @RequestBody Pagamento pagamento){
        pagamento.setIdPagamento(idPagamento);
        pagamentoService.editarPagamento(pagamento);
        return ResponseEntity.ok().body(pagamento);
    }

    @PutMapping("/pagamentosAlteracaoStatus/{idPagamento}")
    public ResponseEntity<Pagamento> modificarStatusPagamento(@PathVariable Integer idPagamento,
                                                              @RequestParam("status") String status){
        pagamentoService.modificarStatusPagamento(idPagamento,status);
        return ResponseEntity.noContent().build();
    }
}
