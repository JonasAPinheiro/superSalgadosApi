package com.example.supersalgadosapi.patterns.factory;

import com.example.supersalgadosapi.model.SalgadoModel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SalgadoCarneFactory implements SalgadoFactory{
    @Override
    public SalgadoModel criar(Integer quantidade) {
        SalgadoModel salgado = new SalgadoModel();
        salgado.setSabor("Carne");
        salgado.setQuantidadeEstoque(quantidade);
        salgado.setPreco(new BigDecimal("5.00"));
        return salgado;
    }
}
