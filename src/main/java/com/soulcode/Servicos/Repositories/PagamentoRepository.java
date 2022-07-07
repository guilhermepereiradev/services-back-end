package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
}
