package com.example.supersalgadosapi.patterns.observer;

import com.example.supersalgadosapi.model.SalgadoModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EstoqueSubject {

    private final List<EstoqueObserver> observers;

    public EstoqueSubject(List<EstoqueObserver> observers) {
        this.observers = observers;
    }

    public List<String> notificarTodos(SalgadoModel salgado) {
        List<String> alertas = new ArrayList<>();
        for (EstoqueObserver observer : observers) {
            String alerta = observer.notificar(salgado);
            if (alerta != null) {
                alertas.add(alerta);
            }
        }
        return alertas;
    }
}