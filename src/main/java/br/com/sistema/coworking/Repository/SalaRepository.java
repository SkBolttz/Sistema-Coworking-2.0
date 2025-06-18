package br.com.sistema.coworking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sistema.coworking.Entity.Sala;

@Repository
public interface SalaRepository extends JpaRepository<Sala,Long>{
    
}
