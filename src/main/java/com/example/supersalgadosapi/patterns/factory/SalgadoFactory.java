package com.example.supersalgadosapi.patterns.factory;

import com.example.supersalgadosapi.model.SalgadoModel;

public interface SalgadoFactory {
    SalgadoModel criar(Integer quantidade);
}
