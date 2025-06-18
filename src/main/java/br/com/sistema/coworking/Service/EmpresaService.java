package br.com.sistema.coworking.Service;

import org.springframework.stereotype.Service;
import br.com.sistema.coworking.Repository.EmpresaRepository;

@Service
public class EmpresaService {
    
    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }
    
}
