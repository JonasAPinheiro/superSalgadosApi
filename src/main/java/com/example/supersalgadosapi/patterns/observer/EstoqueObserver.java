package com.example.supersalgadosapi.patterns.observer;

import com.example.supersalgadosapi.model.SalgadoModel;

public interface EstoqueObserver {
    String notificar(SalgadoModel salgado);
}