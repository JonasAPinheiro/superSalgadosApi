package com.example.supersalgadosapi.patterns.command;

import com.example.supersalgadosapi.model.PedidoModel;
import com.example.supersalgadosapi.patterns.observer.EstoqueSubject;
import com.example.supersalgadosapi.patterns.state.PedidoContext;
import com.example.supersalgadosapi.repository.*;
import org.springframework.stereotype.Component;

@Component
public class CommandInvoker {
    private final PedidoRepository pedidoRepository;
    private final MovimentoEstoqueRepository movimentoEstoqueRepository;
    private final MovimentoFinanceiroRepository movimentoFinanceiroRepository;
    private final ClienteRepository clienteRepository;
    private final SalgadoRepository salgadoRepository;
    private final EstoqueSubject estoqueSubject;

    public CommandInvoker(
            PedidoRepository pedidoRepository,
            MovimentoEstoqueRepository movimentoEstoqueRepository,
            MovimentoFinanceiroRepository movimentoFinanceiroRepository,
            ClienteRepository clienteRepository,
            SalgadoRepository salgadoRepository,
            EstoqueSubject estoqueSubject
    ) {
        this.pedidoRepository = pedidoRepository;
        this.movimentoEstoqueRepository = movimentoEstoqueRepository;
        this.movimentoFinanceiroRepository = movimentoFinanceiroRepository;
        this.clienteRepository = clienteRepository;
        this.salgadoRepository = salgadoRepository;
        this.estoqueSubject = estoqueSubject;
    }

    public void executar(Long pedidoId, Command command) {
        command.executar();
    }

    public void estornar(Long pedidoId) {
        PedidoModel pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        PedidoContext context = new PedidoContext(pedido.getStatus());
        context.estornar();

        FazerPedidoCommand command = new FazerPedidoCommand(
                pedido,
                pedidoRepository,
                movimentoEstoqueRepository,
                movimentoFinanceiroRepository,
                clienteRepository,
                salgadoRepository,
                estoqueSubject
        );

        command.estornar();
    }
}