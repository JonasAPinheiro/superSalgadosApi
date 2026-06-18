package com.example.supersalgadosapi.controller;

import com.example.supersalgadosapi.dto.pedido.PedidoRequestDTO;
import com.example.supersalgadosapi.dto.pedido.PedidoResponseDTO;
import com.example.supersalgadosapi.services.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/criar")
    public ResponseEntity<PedidoResponseDTO> fazerPedido(@RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO response = pedidoService.criarPedido(dto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/estornar/{pedidoId}")
    public ResponseEntity<Void> estornarPedido(@PathVariable Long pedidoId) {
        pedidoService.estornarPedido(pedidoId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/historico/{clienteId}")
    public ResponseEntity<List<PedidoResponseDTO>> historico(@PathVariable Long clienteId) {
        List<PedidoResponseDTO> response = pedidoService.historico(clienteId);
        return ResponseEntity.ok(response);
    }
}
