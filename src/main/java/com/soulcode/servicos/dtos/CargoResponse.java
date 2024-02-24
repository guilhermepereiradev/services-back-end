package com.soulcode.servicos.dtos;

import com.soulcode.servicos.model.Cargo;

public record CargoResponse(Integer id, String nome, String descricao, Double salario) {

    public CargoResponse(Cargo cargo) {
        this(cargo.getId(), cargo.getNome(), cargo.getDescricao(), cargo.getSalario());
    }
}
