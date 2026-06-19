package com.example.supersalgadosapi.patterns.strategy;

import org.springframework.stereotype.Component;

@Component
public class PrecoContext {
    private final PrecoNormalStrategy precoNormalStrategy;
    private final PrecoAtacadoStrategy precoAtacadoStrategy;

    public PrecoContext(PrecoNormalStrategy precoNormalStrategy, PrecoAtacadoStrategy precoAtacadoStrategy) {
        this.precoNormalStrategy = precoNormalStrategy;
        this.precoAtacadoStrategy = precoAtacadoStrategy;
    }

    public PrecoStrategy escolher(Integer quantidade) {
        if (quantidade >= 10) {
            return precoAtacadoStrategy;
        }
        return precoNormalStrategy;
    }
}
