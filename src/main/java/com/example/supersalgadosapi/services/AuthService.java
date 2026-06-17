package com.example.supersalgadosapi.services;

import com.example.supersalgadosapi.dto.auth.LoginRequestDTO;
import com.example.supersalgadosapi.dto.auth.LoginResponseDTO;
import com.example.supersalgadosapi.model.ClienteModel;
import com.example.supersalgadosapi.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    final private ClienteRepository clienteRepository;

    public AuthService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        ClienteModel cliente = clienteRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        if (!cliente.getSenha().equals(dto.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        LoginResponseDTO clienteLogado = new LoginResponseDTO();
        clienteLogado.setId(cliente.getId());
        clienteLogado.setNome(cliente.getNome());
        clienteLogado.setEmail(cliente.getEmail());
        clienteLogado.setSaldo(cliente.getSaldo());

        return clienteLogado;
    }

    public ClienteModel cadastrar(ClienteModel cliente) {
        Optional<ClienteModel> existente = clienteRepository.findByEmail(cliente.getEmail());

        if (existente.isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        return clienteRepository.save(cliente);
    }
}
