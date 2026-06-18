package com.example.supersalgadosapi.patterns.Factory;

import com.example.supersalgadosapi.model.SalgadoModel;

public interface SalgadoFactory {
    SalgadoModel criar(Integer quantidade);
}
