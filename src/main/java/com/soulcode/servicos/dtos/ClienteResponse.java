package com.soulcode.servicos.dtos;

import com.soulcode.servicos.model.Cliente;

import java.util.List;

public record ClienteResponse(Integer id,
                              String nome,
                              String email,
                              List<ChamadoResumoResponse> chamados,
                              EnderecoResponse endereco) {

    public ClienteResponse(Cliente cliente, List<ChamadoResumoResponse> chamados) {
        this(cliente.getId(), cliente.getNome(), cliente.getEmail(), chamados, new EnderecoResponse(cliente.getEndereco()));
    }
}
