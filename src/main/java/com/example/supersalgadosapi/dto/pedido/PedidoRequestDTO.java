package com.example.supersalgadosapi.dto.pedido;

import com.example.supersalgadosapi.dto.itemPedido.ItemPedidoRequestDTO;
import lombok.Data;

import java.util.List;

@Data
public class PedidoRequestDTO {
    private Long clienteId;
    private List<ItemPedidoRequestDTO> itens;
}
