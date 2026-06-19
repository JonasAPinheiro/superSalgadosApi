package com.example.supersalgadosapi.patterns.strategy;

import com.example.supersalgadosapi.model.SalgadoModel;

import java.math.BigDecimal;

public interface PrecoStrategy {
    BigDecimal calcular(SalgadoModel salgado, Integer quantidade);
}
