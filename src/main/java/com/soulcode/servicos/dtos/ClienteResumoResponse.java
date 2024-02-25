package com.soulcode.servicos.dtos;

import com.soulcode.servicos.model.Cliente;

public record ClienteResumoResponse(
        Integer id,
        String nome,
        String email,
        EnderecoResponse endereco) {

    public ClienteResumoResponse(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getEmail(), new EnderecoResponse(cliente.getEndereco()));
    }
}
