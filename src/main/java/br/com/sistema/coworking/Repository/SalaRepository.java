package br.com.sistema.coworking.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.sistema.coworking.Entity.Sala;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {

    Sala findByNome(String nome);

    Page<Sala> findByDisponivelTrue(Pageable pageable);

    Page<Sala> findByDisponivelFalse(Pageable pageable);

}
