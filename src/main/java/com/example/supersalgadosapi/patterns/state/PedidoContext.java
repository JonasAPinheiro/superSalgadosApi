package com.example.supersalgadosapi.patterns.state;

public class PedidoContext {

    private PedidoState estado;

    public PedidoContext(String statusAtual) {
        if ("estornado".equals(statusAtual)) {
            this.estado = new PedidoEstornadoState();
        } else {
            this.estado = new PedidoAtivoState();
        }
    }

    public void estornar() {
        estado.estornar();
        this.estado = new PedidoEstornadoState();
    }

    public String getStatus() {
        return estado.getNomeStatus();
    }
}