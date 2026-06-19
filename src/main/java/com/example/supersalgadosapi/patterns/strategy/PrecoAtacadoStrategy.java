package com.example.supersalgadosapi.patterns.strategy;

import com.example.supersalgadosapi.model.SalgadoModel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PrecoAtacadoStrategy implements PrecoStrategy{
    @Override
    public BigDecimal calcular(SalgadoModel salgado, Integer quantidade) {
        BigDecimal total = salgado.getPreco().multiply(BigDecimal.valueOf(quantidade));

        if (quantidade >= 10) {
            BigDecimal desconto = total.multiply(new BigDecimal("0.10"));
            total = total.subtract(desconto);
        }

        return total;
    }
}
