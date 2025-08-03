package br.com.sistema.coworking.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.com.sistema.coworking.Entity.Visitante;

@Repository
public interface VisitanteRepository extends JpaRepository<Visitante, Long> {

    Optional<Visitante> findByEmail(String email);

    Optional<Visitante> findByCpf(String cpf);

    Visitante findByNomeCompleto(String nomeCompleto);

    @Query("SELECT v FROM Visitante v WHERE v.email LIKE %:email1% OR v.email LIKE %:email2%")
    Page<Visitante> findByEmailContainingFuncionario(@Param("email1") String email1, @Param("email2") String email2,
            Pageable pageable);

    List<Visitante> findByEmpresaIsNull();

}
