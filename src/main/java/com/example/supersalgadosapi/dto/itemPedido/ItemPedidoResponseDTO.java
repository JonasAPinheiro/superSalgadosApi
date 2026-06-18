package com.example.supersalgadosapi.dto.itemPedido;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemPedidoResponseDTO {
    private Long salgadoId;
    private String sabor;
    private Integer quantidade;
    private BigDecimal subtotal;
}
