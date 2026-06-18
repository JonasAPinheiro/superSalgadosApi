package com.example.supersalgadosapi.services;

import com.example.supersalgadosapi.model.SalgadoModel;
import com.example.supersalgadosapi.repository.SalgadoRepository;
import com.example.supersalgadosapi.patterns.Factory.SalgadoFactoryProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalgadoService {
    private final SalgadoRepository salgadoRepository;
    private final SalgadoFactoryProvider factoryProvider;

    public SalgadoService(SalgadoRepository salgadoRepository, SalgadoFactoryProvider factoryProvider) {
        this.salgadoRepository = salgadoRepository;
        this.factoryProvider = factoryProvider;
    }

    public List<SalgadoModel> listar() {
        return salgadoRepository.findAll();
    }

    public SalgadoModel cadastrar(String sabor, Integer quantidade) {
        SalgadoModel salgado = factoryProvider.criar(sabor, quantidade);
        return salgadoRepository.save(salgado);
    }
}
