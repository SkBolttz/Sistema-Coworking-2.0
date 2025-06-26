package br.com.sistema.coworking.Repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sistema.coworking.Entity.Reserva;
import br.com.sistema.coworking.Enum.StatusReserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByVisitanteId(long id);

    Page<Reserva> findByStatus(StatusReserva ativa, Pageable pageable);

}
