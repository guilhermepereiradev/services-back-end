package com.soulcode.servicos.dtos;

import com.soulcode.servicos.model.Pagamento;

public record PagamentoResponse(
        Integer id,
        Double valor,
        String formaPagamento,
        String status) {
    public PagamentoResponse(Pagamento pagamento) {
        this(pagamento.getId(), pagamento.getValor(), pagamento.getFormPagamento(), pagamento.getStatus().getConteudo());
    }
}
