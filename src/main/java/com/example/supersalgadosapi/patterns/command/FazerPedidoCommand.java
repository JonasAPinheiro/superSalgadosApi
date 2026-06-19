package com.example.supersalgadosapi.patterns.command;

import com.example.supersalgadosapi.model.*;
import com.example.supersalgadosapi.repository.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FazerPedidoCommand implements Command{
    private final PedidoModel pedido;
    private final PedidoRepository pedidoRepository;
    private final MovimentoEstoqueRepository movimentoEstoqueRepository;
    private final MovimentoFinanceiroRepository movimentoFinanceiroRepository;
    private final ClienteRepository clienteRepository;
    private final SalgadoRepository salgadoRepository;

    public FazerPedidoCommand(
            PedidoModel pedido,
            PedidoRepository pedidoRepository,
            MovimentoEstoqueRepository movimentoEstoqueRepository,
            MovimentoFinanceiroRepository movimentoFinanceiroRepository,
            ClienteRepository clienteRepository,
            SalgadoRepository salgadoRepository
    ) {
        this.pedido = pedido;
        this.pedidoRepository = pedidoRepository;
        this.movimentoEstoqueRepository = movimentoEstoqueRepository;
        this.movimentoFinanceiroRepository = movimentoFinanceiroRepository;
        this.clienteRepository = clienteRepository;
        this.salgadoRepository = salgadoRepository;
    }

    @Override
    public void executar() {
        pedido.setStatus("ativo");
        pedido.setDataHora(LocalDateTime.now());
        pedidoRepository.save(pedido);

        for (ItemPedidoModel item : pedido.getItens()) {
            MovimentoEstoqueModel movimentoEstoque = new MovimentoEstoqueModel();
            movimentoEstoque.setSalgado(item.getSalgado());
            movimentoEstoque.setTipo("saida");
            movimentoEstoque.setQuantidade(item.getQuantidade());
            movimentoEstoque.setDataHora(LocalDateTime.now());
            movimentoEstoqueRepository.save(movimentoEstoque);

            SalgadoModel salgado = item.getSalgado();
            salgado.setQuantidadeEstoque(salgado.getQuantidadeEstoque() - item.getQuantidade());
            salgadoRepository.save(salgado);
        }

        BigDecimal total = pedido.getItens().stream()
                .map(ItemPedidoModel::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        MovimentoFinanceiroModel movimentoFinanceiro = new MovimentoFinanceiroModel();
        movimentoFinanceiro.setCliente(pedido.getCliente());
        movimentoFinanceiro.setPedido(pedido);
        movimentoFinanceiro.setTipo("debito");
        movimentoFinanceiro.setValor(total);
        movimentoFinanceiro.setDataHora(LocalDateTime.now());
        movimentoFinanceiroRepository.save(movimentoFinanceiro);

        ClienteModel cliente = pedido.getCliente();
        cliente.setSaldo(cliente.getSaldo() - total.doubleValue());
        clienteRepository.save(cliente);
    }

    @Override
    public void estornar() {
        pedido.setStatus("estornado");
        pedidoRepository.save(pedido);

        for (ItemPedidoModel item : pedido.getItens()) {
            MovimentoEstoqueModel movimentoEstoque = new MovimentoEstoqueModel();
            movimentoEstoque.setSalgado(item.getSalgado());
            movimentoEstoque.setTipo("entrada");
            movimentoEstoque.setQuantidade(item.getQuantidade());
            movimentoEstoque.setDataHora(LocalDateTime.now());
            movimentoEstoqueRepository.save(movimentoEstoque);

            SalgadoModel salgado = item.getSalgado();
            salgado.setQuantidadeEstoque(salgado.getQuantidadeEstoque() + item.getQuantidade());
            salgadoRepository.save(salgado);
        }

        BigDecimal total = pedido.getItens().stream()
                .map(ItemPedidoModel::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        MovimentoFinanceiroModel movimentoFinanceiro = new MovimentoFinanceiroModel();
        movimentoFinanceiro.setCliente(pedido.getCliente());
        movimentoFinanceiro.setPedido(pedido);
        movimentoFinanceiro.setTipo("credito");
        movimentoFinanceiro.setValor(total);
        movimentoFinanceiro.setDataHora(LocalDateTime.now());
        movimentoFinanceiroRepository.save(movimentoFinanceiro);

        ClienteModel cliente = pedido.getCliente();
        cliente.setSaldo(cliente.getSaldo() + total.doubleValue());
        clienteRepository.save(cliente);
    }
}
