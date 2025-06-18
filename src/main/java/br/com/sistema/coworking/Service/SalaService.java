package br.com.sistema.coworking.Service;

import org.springframework.stereotype.Service;

import br.com.sistema.coworking.Repository.SalaRepository;

@Service
public class SalaService {
    
    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }
}
