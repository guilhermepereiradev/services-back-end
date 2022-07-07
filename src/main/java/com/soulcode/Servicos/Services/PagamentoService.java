package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Chamado;
import com.soulcode.Servicos.Models.Pagamento;
import com.soulcode.Servicos.Repositories.ChamadoRepository;
import com.soulcode.Servicos.Repositories.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    ChamadoRepository chamadoRepository;
    public List<Pagamento> mostrarPagamentos(){
        return pagamentoRepository.findAll();
    }

    public Pagamento mostrarPagamentoPeloId(Integer idPagamento){
        Optional<Pagamento> pagamento = pagamentoRepository.findById(idPagamento);
        return pagamento.orElseThrow();
    }
    public Pagamento cadastrarPagamento(Pagamento pagamento, Integer idChamado){
        Optional<Chamado> chamado = chamadoRepository.findById(idChamado);
        pagamento.setIdPagamento(chamado.get().getIdChamado());
        pagamentoRepository.save(pagamento);
        chamado.get().setPagamento(pagamento);
        chamadoRepository.save(chamado.get());
        return pagamento;
    }

    public Pagamento atualizarPagamento(Pagamento pagamento, Integer idPagamento){
        Pagamento pagamentoAntigo = pagamentoRepository.getById(idPagamento);
        Optional<Chamado> chamado = chamadoRepository.findById(idPagamento);
        pagamento.setIdPagamento(pagamentoAntigo.getIdPagamento());
        pagamentoRepository.save(pagamento);
        chamado.get().setPagamento(pagamento);
        chamadoRepository.save(chamado.get());
        return pagamento;
    }
}
