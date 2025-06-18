package br.com.sistema.coworking.Service;

import org.springframework.stereotype.Service;

import br.com.sistema.coworking.Repository.VisitanteRepository;

@Service
public class VisitanteService {
    
    private final VisitanteRepository visitanteRepository;

    public VisitanteService(VisitanteRepository visitanteRepository) {
        this.visitanteRepository = visitanteRepository;
    }
}
