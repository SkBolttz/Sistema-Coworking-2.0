package br.com.sistema.coworking.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sistema.coworking.Entity.Estacao;

@Repository
public interface EstacaoRepository extends JpaRepository<Estacao,Long>{

    Page<Estacao> findByAtivoFalse(Pageable pageable);

    Page<Estacao> findByAtivoTrue(Pageable pageable);
    
}
