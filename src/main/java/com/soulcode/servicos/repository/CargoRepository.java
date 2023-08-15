package com.soulcode.servicos.repository;

import com.soulcode.servicos.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CargoRepository extends JpaRepository<Cargo, Integer> {
    Optional<Cargo> findByNome(String nome);
}
