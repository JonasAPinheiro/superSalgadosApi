package com.example.supersalgadosapi.patterns.state;

public class PedidoAtivoState implements PedidoState {

    @Override
    public void estornar() {
        // Estado ativo permite estornar — não faz nada aqui,
        // a transição é tratada pelo Context
    }

    @Override
    public String getNomeStatus() {
        return "ativo";
    }
}