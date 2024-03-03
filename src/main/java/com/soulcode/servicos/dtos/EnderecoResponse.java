package com.soulcode.servicos.dtos;

import com.soulcode.servicos.model.Endereco;

public record EnderecoResponse(
        String rua,
        String bairro,
        String cidade,
        String uf) {
    public EnderecoResponse(Endereco endereco) {
        this(endereco.getRua(), endereco.getBairro(), endereco.getCidade(), endereco.getUf());
    }
}
