package com.example.supersalgadosapi.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class HistoricoResponseDTO {
    private Long pedidoId;
    private String status;
    private LocalDateTime dataHora;
    private BigDecimal total;
}