package com.example.supersalgadosapi.patterns.Factory;

import com.example.supersalgadosapi.model.SalgadoModel;
import com.example.supersalgadosapi.patterns.Factory.SalgadoCarneFactory;
import com.example.supersalgadosapi.patterns.Factory.SalgadoFrangoFactory;
import org.springframework.stereotype.Component;

@Component
public class SalgadoFactoryProvider {
    private final SalgadoFrangoFactory frangoFactory;
    private final SalgadoCarneFactory carneFactory;

    public SalgadoFactoryProvider(
            SalgadoFrangoFactory frangoFactory,
            SalgadoCarneFactory carneFactory
    ) {
        this.frangoFactory = frangoFactory;
        this.carneFactory = carneFactory;
    }

    public SalgadoModel criar(String sabor, Integer quantidade) {
        return switch (sabor.toUpperCase()) {
            case "Frango" -> frangoFactory.criar(quantidade);
            case "Carne" -> carneFactory.criar(quantidade);
            default -> throw new RuntimeException("Sabor não encontrado: " + sabor);
        };
    }
}