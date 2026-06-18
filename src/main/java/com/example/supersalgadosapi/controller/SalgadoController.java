package com.example.supersalgadosapi.controller;

import com.example.supersalgadosapi.model.SalgadoModel;
import com.example.supersalgadosapi.services.SalgadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salgado")
public class SalgadoController {

    private final com.example.supersalgadosapi.services.SalgadoService salgadoService;

    public SalgadoController(SalgadoService salgadoService) {
        this.salgadoService = salgadoService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<SalgadoModel> cadastrar(@RequestParam String sabor, @RequestParam Integer quantidade) {
        SalgadoModel salgado = salgadoService.cadastrar(sabor, quantidade);
        return ResponseEntity.ok(salgado);
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(salgadoService.listar());
    }
}