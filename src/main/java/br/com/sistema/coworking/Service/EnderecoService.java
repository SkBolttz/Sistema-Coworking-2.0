package br.com.sistema.coworking.Service;

import org.springframework.stereotype.Service;
import br.com.sistema.coworking.Repository.EnderecoRepository;

@Service
public class EnderecoService {
    
    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

}
