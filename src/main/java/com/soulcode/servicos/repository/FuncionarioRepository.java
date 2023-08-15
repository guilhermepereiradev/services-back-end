package com.soulcode.servicos.repository;

import com.soulcode.servicos.model.Cargo;
import com.soulcode.servicos.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    Optional<Funcionario> findByEmail(String email);

    List<Funcionario> findByCargo(Optional<Cargo> cargo);

//    Optional<Funcionario> findByNome(String nome);

//    Optional<Funcionario> findByNomeAndEmail(String nome, String email);
}
