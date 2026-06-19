package com.example.supersalgadosapi.patterns.strategy;

import com.example.supersalgadosapi.model.SalgadoModel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PrecoNormalStrategy implements PrecoStrategy{
    @Override
    public BigDecimal calcular(SalgadoModel salgado, Integer quantidade) {
        return salgado.getPreco().multiply(BigDecimal.valueOf(quantidade));
    }
}
