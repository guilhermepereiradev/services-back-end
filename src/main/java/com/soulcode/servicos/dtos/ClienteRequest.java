package com.soulcode.servicos.dtos;

import com.soulcode.servicos.model.Endereco;

public record ClienteRequest(
                             String nome,
                             String email,
                             Endereco endereco) {
}
