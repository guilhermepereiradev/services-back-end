package com.soulcode.servicos.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soulcode.servicos.model.Chamado;
import com.soulcode.servicos.model.Cliente;
import com.soulcode.servicos.model.Endereco;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public record ClienteResumoResponse(
        Integer id,
        String nome,
        String email,
        EnderecoResponse endereco) {

    public ClienteResumoResponse(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getEmail(), new EnderecoResponse(cliente.getEndereco()));
    }
}
