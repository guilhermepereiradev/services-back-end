package com.soulcode.servicos.dtos;

import com.soulcode.servicos.model.Cargo;

public record CargoResumoResponse(Integer id, String nome) {

    public CargoResumoResponse(Cargo cargo) {
        this(cargo.getId(), cargo.getNome());
    }
}
