package com.soulcode.servicos.dtos;

import com.soulcode.servicos.model.StatusChamado;

import java.util.Date;

public record ChamadoResponse(
        Integer id,
        String descricao,
        Date dataEntrada,
        StatusChamado status,
        FuncionarioResumoResponse funcionario,
        ClienteResumoResponse cliente,
        PagamentoResponse pagamento) {
}
