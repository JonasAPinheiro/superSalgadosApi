package com.example.supersalgadosapi.controller;

import com.example.supersalgadosapi.dto.auth.LoginRequestDTO;
import com.example.supersalgadosapi.dto.auth.LoginResponseDTO;
import com.example.supersalgadosapi.model.ClienteModel;
import com.example.supersalgadosapi.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    final private AuthService authService;

    public  AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        LoginResponseDTO response = authService.login(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ClienteModel> cadastrar(@RequestBody ClienteModel cliente) {
        ClienteModel clienteNovo = authService.cadastrar(cliente);
        return ResponseEntity.ok(clienteNovo);
    }
}
