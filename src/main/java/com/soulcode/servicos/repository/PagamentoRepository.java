package com.soulcode.servicos.repository;

import com.soulcode.servicos.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
    @Query(value = "SELECT * FROM pagamento WHERE status = :status", nativeQuery = true)
    List<Pagamento> findByStatus(String status);

    @Query(value = "SELECT pagamento.*, chamado.id_chamado, chamado.titulo, cliente.id_cliente, cliente.nome \n" +
            "FROM chamado RIGHT JOIN pagamento ON chamado.id_chamado = pagamento.id_pagamento  \n" +
            "LEFT JOIN cliente ON cliente.id_cliente = chamado.id_cliente;\n",nativeQuery = true)
    List<List> orcamentoCOmServicoCliente();
}
