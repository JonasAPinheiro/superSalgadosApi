package com.example.supersalgadosapi.dto.auth;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private Double saldo;
}
