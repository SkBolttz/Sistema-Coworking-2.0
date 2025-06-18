package br.com.sistema.coworking.Service;

import org.springframework.stereotype.Service;

import br.com.sistema.coworking.Repository.EstacaoRepository;

@Service
public class EstacaoService {
    
    private final EstacaoRepository estacaoRepository;
    
    public EstacaoService(EstacaoRepository estacaoRepository) {
        this.estacaoRepository = estacaoRepository;
    }
}
