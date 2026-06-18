package com.example.supersalgadosapi.patterns.Command;

import com.example.supersalgadosapi.model.PedidoModel;
import com.example.supersalgadosapi.repository.*;
import org.springframework.stereotype.Component;

@Component
public class CommandInvoker {
    private final PedidoRepository pedidoRepository;
    private final MovimentoEstoqueRepository movimentoEstoqueRepository;
    private final MovimentoFinanceiroRepository movimentoFinanceiroRepository;
    private final ClienteRepository clienteRepository;
    private final SalgadoRepository salgadoRepository;

    public CommandInvoker(
            PedidoRepository pedidoRepository,
            MovimentoEstoqueRepository movimentoEstoqueRepository,
            MovimentoFinanceiroRepository movimentoFinanceiroRepository,
            ClienteRepository clienteRepository,
            SalgadoRepository salgadoRepository
    ) {
        this.pedidoRepository = pedidoRepository;
        this.movimentoEstoqueRepository = movimentoEstoqueRepository;
        this.movimentoFinanceiroRepository = movimentoFinanceiroRepository;
        this.clienteRepository = clienteRepository;
        this.salgadoRepository = salgadoRepository;
    }

    public void executar(Long pedidoId, Command command) {
        command.executar();
    }

    public void estornar(Long pedidoId) {
        PedidoModel pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (pedido.getStatus().equals("estornado")) {
            throw new RuntimeException("Pedido já foi estornado");
        }

        FazerPedidoCommand command = new FazerPedidoCommand(
                pedido,
                pedidoRepository,
                movimentoEstoqueRepository,
                movimentoFinanceiroRepository,
                clienteRepository,
                salgadoRepository
        );

        command.estornar();
    }
}