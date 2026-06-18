package com.example.supersalgadosapi.patterns.Factory;

import com.example.supersalgadosapi.model.SalgadoModel;

import java.math.BigDecimal;

public class SalgadoFrangoFactory implements SalgadoFactory{

    @Override
    public SalgadoModel criar(Integer quantidade) {
        SalgadoModel salgado = new SalgadoModel();
        salgado.setSabor("Frango");
        salgado.setQuantidadeEstoque(quantidade);
        salgado.setPreco(new BigDecimal("8.00"));
        return salgado;
    }
}
