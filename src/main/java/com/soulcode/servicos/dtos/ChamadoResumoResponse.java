package com.soulcode.servicos.dtos;

import com.soulcode.servicos.model.Chamado;
import com.soulcode.servicos.model.StatusChamado;

import java.util.Date;

public record ChamadoResumoResponse(
        Integer id,
        String descricao,
        Date dataEntrada,
        StatusChamado status,
        PagamentoResponse pagamento) {
    public ChamadoResumoResponse(Chamado chamado) {
        this(chamado.getId(), chamado.getDescricao(), chamado.getDataEntrada(), chamado.getStatus(), new PagamentoResponse(chamado.getPagamento()));
    }
}
