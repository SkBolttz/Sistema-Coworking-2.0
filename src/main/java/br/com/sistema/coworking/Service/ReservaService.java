package br.com.sistema.coworking.Service;

import org.springframework.stereotype.Service;

import br.com.sistema.coworking.Repository.ReservaRepository;

@Service
public class ReservaService {
    
    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }
}
