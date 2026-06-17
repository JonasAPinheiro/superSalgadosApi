package com.example.supersalgadosapi.repository;

import com.example.supersalgadosapi.model.MovimentoEstoqueModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentoEstoqueRepository extends JpaRepository<MovimentoEstoqueModel, Long> {
}
