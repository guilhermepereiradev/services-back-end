package com.soulcode.servicos.model.exception;

import com.soulcode.servicos.service.exceptions.EntidadeNaoEncontradaException;

public class CargoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public CargoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public CargoNaoEncontradoException(Integer cargoId) {
        this(String.format("Cargo não encontrado para o id: %d", cargoId));
    }
}
