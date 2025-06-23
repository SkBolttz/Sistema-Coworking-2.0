package br.com.sistema.coworking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.sistema.coworking.Entity.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa,Long>{

    Empresa findByCnpj(String cnpj);

    Empresa findByNomeFantasia(String nomeFantasia);

    Empresa findByRazaoSocial(String razaoSocial);

    Empresa findByEmail(String email);

    Empresa findByTelefone(String telefone);
    
}
