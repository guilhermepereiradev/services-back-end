package com.soulcode.servicos.model.exception;

import com.soulcode.servicos.service.exceptions.EntidadeNaoEncontradaException;

public class FuncionarioNaoEncontradoException extends EntidadeNaoEncontradaException {
    public FuncionarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public FuncionarioNaoEncontradoException(Integer funcionarioId) {
        this(String.format("Funcionário não encontrado para o id: %d", funcionarioId));
    }
}
