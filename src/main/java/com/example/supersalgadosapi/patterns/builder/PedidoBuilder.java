package com.example.supersalgadosapi.patterns.builder;

import com.example.supersalgadosapi.model.ClienteModel;
import com.example.supersalgadosapi.model.ItemPedidoModel;
import com.example.supersalgadosapi.model.PedidoModel;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoBuilder {

    private final PedidoModel pedido;

    public PedidoBuilder() {
        this.pedido = new PedidoModel();
    }

    public PedidoBuilder comCliente(ClienteModel cliente) {
        pedido.setCliente(cliente);
        return this;
    }

    public PedidoBuilder comItens(List<ItemPedidoModel> itens) {
        pedido.setItens(itens);
        return this;
    }

    public PedidoBuilder comStatus(String status) {
        pedido.setStatus(status);
        return this;
    }

    public PedidoBuilder comDataHora(LocalDateTime dataHora) {
        pedido.setDataHora(dataHora);
        return this;
    }

    public PedidoModel build() {
        return pedido;
    }
}
