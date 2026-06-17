package com.example.supersalgadosapi.repository;

import com.example.supersalgadosapi.model.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {
    List<PedidoModel> findByClienteId(Long clienteId);
}
