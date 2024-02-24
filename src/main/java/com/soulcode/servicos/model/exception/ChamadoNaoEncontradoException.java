package com.soulcode.servicos.model.exception;

import com.soulcode.servicos.service.exceptions.EntidadeNaoEncontradaException;

public class ChamadoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public ChamadoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ChamadoNaoEncontradoException(Integer cargoId) {
        this(String.format("Chamado não encontrado para o id: %d", cargoId));
    }
}
