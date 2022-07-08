package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
    @Query(value = "SELECT * FROM pagamento WHERE status = :status", nativeQuery = true)
    List<Pagamento> findByStatus(String status);
}
