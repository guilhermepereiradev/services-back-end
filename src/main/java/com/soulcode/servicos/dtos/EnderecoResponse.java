package com.soulcode.servicos.dtos;

import com.soulcode.servicos.model.Endereco;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

public record EnderecoResponse(
        Integer id,
        String rua,
        String bairro,
        String cidade,
        String uf) {
    public EnderecoResponse(Endereco endereco) {
        this(endereco.getId(), endereco.getRua(), endereco.getBairro(), endereco.getCidade(), endereco.getUf());
    }
}
