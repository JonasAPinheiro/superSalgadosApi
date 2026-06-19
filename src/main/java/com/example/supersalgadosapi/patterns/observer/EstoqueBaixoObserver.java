package com.example.supersalgadosapi.patterns.observer;

import com.example.supersalgadosapi.model.SalgadoModel;
import org.springframework.stereotype.Component;

@Component
public class EstoqueBaixoObserver implements EstoqueObserver {

    private static final int LIMITE_ESTOQUE_BAIXO = 10;

    @Override
    public String notificar(SalgadoModel salgado) {
        if (salgado.getQuantidadeEstoque() <= LIMITE_ESTOQUE_BAIXO) {
            return "Estoque baixo do sabor " + salgado.getSabor()
                    + " — restam apenas " + salgado.getQuantidadeEstoque() + " unidades!";
        }
        return null;
    }
}