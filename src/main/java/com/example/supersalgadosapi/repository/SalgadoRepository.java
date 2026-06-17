package com.example.supersalgadosapi.repository;

import com.example.supersalgadosapi.model.SalgadoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalgadoRepository extends JpaRepository<SalgadoModel, Long> {
}
