package br.com.sistema.coworking.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.sistema.coworking.Entity.Visitante;

@Repository
public interface VisitanteRepository extends JpaRepository<Visitante,Long>
{

    Optional<Visitante> findByEmail(String email);

    Optional<Visitante> findByCpf(String cpf);
    
}
