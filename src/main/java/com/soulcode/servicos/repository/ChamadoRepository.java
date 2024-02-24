package com.soulcode.servicos.repository;

import com.soulcode.servicos.model.Chamado;
import com.soulcode.servicos.model.Cliente;
import com.soulcode.servicos.model.Funcionario;
import com.soulcode.servicos.model.StatusChamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {
    List<Chamado> findByCliente(Cliente cliente);
    List<Chamado> findByFuncionario(Funcionario funcionario);

    @Query("from Chamado c where c.status = :status")
    List<Chamado> findByStatus(StatusChamado status);

    @Query("from Chamado c where c.dataEntrada between :date1 and :date2")
    List<Chamado> findByIntervaloData(Date date1, Date date2);

}
