package com.soulcode.servicos.service;

import com.soulcode.servicos.model.Chamado;
import com.soulcode.servicos.model.Pagamento;
import com.soulcode.servicos.model.StatusPagamento;
import com.soulcode.servicos.repository.ChamadoRepository;
import com.soulcode.servicos.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    ChamadoRepository chamadoRepository;

    @Cacheable("pagamentosCache")
    public List<Pagamento> mostrarTodosPagamentos(){
        return pagamentoRepository.findAll();
    }

    @Cacheable(value = "pagamentosCache", key = "#idPagamento")
    public Pagamento mostrarPagamentoPeloId(Integer idPagamento){
        Optional<Pagamento> pagamento = pagamentoRepository.findById(idPagamento);
        return pagamento.orElseThrow();
    }

    @Cacheable(value = "pagamentosCache", key = "#status")
    public List<Pagamento> mostrarPagamentosPeloStatus(String status){
        return pagamentoRepository.findByStatus(status);
    }

    @CachePut(value = "pagamentosCache", key = "#pagamento.idPagamento")
    public Pagamento cadastrarPagamento(Pagamento pagamento, Integer idChamado){
        Optional<Chamado> chamado = chamadoRepository.findById(idChamado);
        if (chamado.isPresent()){
            pagamento.setId(idChamado);
            pagamento.setStatus(StatusPagamento.LANCADO);
            pagamentoRepository.save(pagamento);

            chamado.get().setPagamento(pagamento);
            chamadoRepository.save(chamado.get());
            return pagamento;
        }else{
            throw new RuntimeException();
        }

    }
    @CachePut(value = "pagamentosCache", key = "#pagamento.idPagamento")
    public Pagamento editarPagamento(Pagamento pagamento){
        return pagamentoRepository.save(pagamento);
    }

    @CachePut(value = "pagamentosCache", key = "#idPagamento")
    public Pagamento modificarStatusPagamento(Integer idPagamento,String status){
        Pagamento pagamento = mostrarPagamentoPeloId(idPagamento);

        switch (status){
            case "LANCADO":
                pagamento.setStatus(StatusPagamento.LANCADO);
                break;
            case "QUITADO":
                pagamento.setStatus(StatusPagamento.QUITADO);
                break;
        }
        return pagamentoRepository.save(pagamento);
    }

    @Cacheable(value = "pagamentosCache")
    public List<List> orcamentoComServicoCliente(){
        return pagamentoRepository.orcamentoCOmServicoCliente();
    }
}
