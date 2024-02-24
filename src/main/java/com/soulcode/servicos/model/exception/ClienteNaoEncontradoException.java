package com.soulcode.servicos.model.exception;

import com.soulcode.servicos.service.exceptions.EntidadeNaoEncontradaException;

public class ClienteNaoEncontradoException extends EntidadeNaoEncontradaException {
    public ClienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ClienteNaoEncontradoException(Integer cargoId) {
        this(String.format("Cliente não encontrado para o id: %d", cargoId));
    }
}
