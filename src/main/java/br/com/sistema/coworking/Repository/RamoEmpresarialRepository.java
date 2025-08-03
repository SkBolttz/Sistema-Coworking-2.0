package br.com.sistema.coworking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.sistema.coworking.Entity.RamoEmpresarial;

@Repository
public interface RamoEmpresarialRepository extends JpaRepository<RamoEmpresarial, Long> {

    RamoEmpresarial findByRamo(String ramo);

}
