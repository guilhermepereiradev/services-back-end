package com.soulcode.servicos.dtos;

public record ClienteRequest(
                             String nome,
                             String email,
                             EnderecoResponse endereco) {
}
