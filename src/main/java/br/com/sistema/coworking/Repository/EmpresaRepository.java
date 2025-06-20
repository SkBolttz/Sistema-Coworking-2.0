package br.com.sistema.coworking.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sistema.coworking.Entity.Empresa;
import br.com.sistema.coworking.Entity.Visitante;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa,Long>{

    Optional<Visitante> findByCnpj(String cnpj);
    
}
