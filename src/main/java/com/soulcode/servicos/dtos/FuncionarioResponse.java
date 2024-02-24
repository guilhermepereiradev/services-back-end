package com.soulcode.servicos.dtos;

import java.util.List;

public record FuncionarioResponse(
        Integer id,
        String nome,
        String email,
        String foto,
        List<ChamadoResumoResponse> chamados,
        CargoResponse cargo
) {}
