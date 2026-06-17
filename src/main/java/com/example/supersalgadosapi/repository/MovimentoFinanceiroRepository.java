package com.example.supersalgadosapi.repository;

import com.example.supersalgadosapi.model.MovimentoFinanceiroModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentoFinanceiroRepository extends JpaRepository<MovimentoFinanceiroModel, Long> {
    List<MovimentoFinanceiroModel> findByClienteId(Long clienteId);
}
