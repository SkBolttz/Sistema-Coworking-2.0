package br.com.sistema.coworking.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.sistema.coworking.Entity.RamoEmpresarial;
import br.com.sistema.coworking.Exception.Records.Ramo.CadastroRamoException;
import br.com.sistema.coworking.Repository.RamoEmpresarialRepository;

@Service
public class RamoService {

    @Autowired
    private RamoEmpresarialRepository ramoEmpresarialRepository;

    public void criarRamo(RamoEmpresarial ramo) {

        RamoEmpresarial ramoExiste = ramoEmpresarialRepository.findByRamo(ramo.getRamo());

        if (ramoExiste != null) {
            throw new CadastroRamoException("Ramo ja cadastrado", "");
        }

        ramoEmpresarialRepository.save(ramo);
    }

    public List<RamoEmpresarial> obterRamos() {
        return (ramoEmpresarialRepository.findAll());
    }
}
