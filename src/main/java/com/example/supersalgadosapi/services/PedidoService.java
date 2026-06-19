package com.example.supersalgadosapi.services;

import com.example.supersalgadosapi.dto.itemPedido.ItemPedidoResponseDTO;
import com.example.supersalgadosapi.dto.pedido.PedidoRequestDTO;
import com.example.supersalgadosapi.dto.pedido.PedidoResponseDTO;
import com.example.supersalgadosapi.model.ClienteModel;
import com.example.supersalgadosapi.model.ItemPedidoModel;
import com.example.supersalgadosapi.model.PedidoModel;
import com.example.supersalgadosapi.model.SalgadoModel;
import com.example.supersalgadosapi.patterns.command.CommandInvoker;
import com.example.supersalgadosapi.patterns.command.FazerPedidoCommand;
import com.example.supersalgadosapi.patterns.observer.EstoqueSubject;
import com.example.supersalgadosapi.patterns.strategy.PrecoContext;
import com.example.supersalgadosapi.patterns.strategy.PrecoStrategy;
import com.example.supersalgadosapi.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {
    private final ClienteRepository clienteRepository;
    private final SalgadoRepository salgadoRepository;
    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final MovimentoEstoqueRepository movimentoEstoqueRepository;
    private final MovimentoFinanceiroRepository movimentoFinanceiroRepository;
    private final CommandInvoker commandInvoker;
    private final PrecoContext precoContext;
    private final EstoqueSubject estoqueSubject;

    public PedidoService(
            ClienteRepository clienteRepository,
            SalgadoRepository salgadoRepository,
            PedidoRepository pedidoRepository,
            ItemPedidoRepository itemPedidoRepository,
            MovimentoEstoqueRepository movimentoEstoqueRepository,
            MovimentoFinanceiroRepository movimentoFinanceiroRepository,
            CommandInvoker commandInvoker,
            PrecoContext precoContext,
            EstoqueSubject estoqueSubject
    ) {
        this.clienteRepository = clienteRepository;
        this.salgadoRepository = salgadoRepository;
        this.pedidoRepository = pedidoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.movimentoEstoqueRepository = movimentoEstoqueRepository;
        this.movimentoFinanceiroRepository = movimentoFinanceiroRepository;
        this.commandInvoker = commandInvoker;
        this.precoContext = precoContext;
        this.estoqueSubject = estoqueSubject;
    }

    public PedidoResponseDTO criarPedido(PedidoRequestDTO dto) {
        ClienteModel cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        PedidoModel pedido = new PedidoModel();
        pedido.setCliente(cliente);

        List<ItemPedidoModel> itens = new ArrayList<>();
        for (var itemDTO : dto.getItens()) {
            SalgadoModel salgado = salgadoRepository.findById(itemDTO.getSalgadoId())
                    .orElseThrow(() -> new RuntimeException("Salgado não encontrado"));

            if (salgado.getQuantidadeEstoque() < itemDTO.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para: " + salgado.getSabor());
            }

            ItemPedidoModel item = new ItemPedidoModel();
            item.setPedido(pedido);
            item.setSalgado(salgado);
            item.setQuantidade(itemDTO.getQuantidade());

            PrecoStrategy strategy = precoContext.escolher(itemDTO.getQuantidade());
            BigDecimal subtotal = strategy.calcular(salgado, itemDTO.getQuantidade());
            item.setSubtotal(subtotal);

            itens.add(item);
        }

        pedido.setItens(itens);

        BigDecimal total = itens.stream()
                .map(ItemPedidoModel::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (cliente.getSaldo() < total.doubleValue()) {
            throw new RuntimeException("Saldo insuficiente");
        }

        FazerPedidoCommand command = new FazerPedidoCommand(
                pedido,
                pedidoRepository,
                movimentoEstoqueRepository,
                movimentoFinanceiroRepository,
                clienteRepository,
                salgadoRepository,
                estoqueSubject
        );

        commandInvoker.executar(pedido.getId(), command);

        PedidoResponseDTO response = montarResponse(pedido, total);
        response.setAlertasEstoque(command.getAlertasEstoque());
        return response;
    }

    public void estornarPedido(Long pedidoId) {
        commandInvoker.estornar(pedidoId);
    }
    public List<PedidoResponseDTO> historico(Long clienteId) {
        List<PedidoModel> pedidos = pedidoRepository.findByClienteId(clienteId);
        List<PedidoResponseDTO> response = new ArrayList<>();

        for (PedidoModel pedido : pedidos) {
            BigDecimal total = pedido.getItens().stream()
                    .map(ItemPedidoModel::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            response.add(montarResponse(pedido, total));
        }

        return response;
    }

    private PedidoResponseDTO montarResponse(PedidoModel pedido, BigDecimal total) {
        PedidoResponseDTO response = new PedidoResponseDTO();
        response.setId(pedido.getId());
        response.setStatus(pedido.getStatus());
        response.setDataHora(pedido.getDataHora());
        response.setTotal(total);

        List<ItemPedidoResponseDTO> itensResponse = new ArrayList<>();
        for (ItemPedidoModel item : pedido.getItens()) {
            ItemPedidoResponseDTO itemResponse = new ItemPedidoResponseDTO();
            itemResponse.setSalgadoId(item.getSalgado().getId());
            itemResponse.setSabor(item.getSalgado().getSabor());
            itemResponse.setQuantidade(item.getQuantidade());
            itemResponse.setSubtotal(item.getSubtotal());
            itensResponse.add(itemResponse);
        }

        response.setItens(itensResponse);
        return response;
    }
}
