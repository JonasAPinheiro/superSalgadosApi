package com.example.supersalgadosapi.patterns.state;

public class PedidoEstornadoState implements PedidoState {

    @Override
    public void estornar() {
        throw new RuntimeException("Pedido já foi estornado");
    }

    @Override
    public String getNomeStatus() {
        return "estornado";
    }
}