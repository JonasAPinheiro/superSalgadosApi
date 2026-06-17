package com.example.supersalgadosapi.dto.pedido;

import com.example.supersalgadosapi.dto.itemPedido.ItemPedidoResponseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoResponseDTO {
    private Long id;
    private String status;
    private LocalDateTime dataHora;
    private BigDecimal total;
    private List<ItemPedidoResponseDTO> itens;
}
