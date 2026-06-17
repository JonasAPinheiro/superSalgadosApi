package com.example.supersalgadosapi.dto.pedido;

import lombok.Data;

@Data
public class PedidoResponseDTO {
    private Long id;
    private String status;
    private LocalDateTime dataHora;
    private BigDecimal total;
    private List<ItemPedidoResponseDTO> itens;
}
