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
    public List<Pagamento> mostrarPagamentos(){
        return pagamentoService.mostrarPagamentos();
    }

    @GetMapping("/pagamento/{idPagamento}")
    public ResponseEntity<Pagamento> mostrarPagamentoPeloId(@PathVariable Integer idPagamento){
        Pagamento pagamento = pagamentoService.mostrarPagamentoPeloId(idPagamento);
        return ResponseEntity.ok().body(pagamento);
    }

    @PostMapping("/pagamento/{idChamado}")
    public ResponseEntity<Pagamento> cadastrarPagamento(@PathVariable Integer idChamado, @RequestBody Pagamento pagamento){
        pagamento = pagamentoService.cadastrarPagamento(pagamento, idChamado);
        URI novaURI = ServletUriComponentsBuilder.fromCurrentRequest().path("id").buildAndExpand(pagamento.getIdPagamento()).toUri();
        return ResponseEntity.created(novaURI).body(pagamento);
    }

    @PutMapping("pagamento/{idPagamento}")
    public ResponseEntity<Pagamento> atualizarPagamento(@PathVariable Integer idPagamento, @RequestBody Pagamento pagamento){
        pagamento = pagamentoService.atualizarPagamento(pagamento, idPagamento);
        return  ResponseEntity.ok().body(pagamento);
    }
}
