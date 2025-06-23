package br.com.sistema.coworking.Service;

import java.time.LocalDateTime;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.sistema.coworking.Entity.Empresa;
import br.com.sistema.coworking.Entity.Visitante;
import br.com.sistema.coworking.Enum.TipoVisitante;
import br.com.sistema.coworking.Exception.Records.Cadastro.CpfExecption;
import br.com.sistema.coworking.Exception.Records.Cadastro.EmailException;
import br.com.sistema.coworking.Exception.Records.Cadastro.EmpresaCadastroException;
import br.com.sistema.coworking.Repository.EmpresaRepository;
import br.com.sistema.coworking.Repository.VisitanteRepository;

@Service
public class LoginService {

    private final VisitanteRepository visitanteRepository;
    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(VisitanteRepository visitanteRepository, EmpresaRepository empresaRepository,
            PasswordEncoder passwordEncoder) {
        this.visitanteRepository = visitanteRepository;
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registro(Visitante visitante) {

        if (visitanteRepository.findByEmail(visitante.getEmail()).isPresent()) {
            throw new EmailException("Email já cadastrado", "O email fornecido já está em uso.");
        }

        if (visitanteRepository.findByCpf(visitante.getCpf()).isPresent()) {
            throw new CpfExecption("CPF já cadastrado", "O CPF fornecido já está em uso.");
        }

        if (visitante.getEmpresa() != null) {
            Empresa empresaExiste = empresaRepository.findByCnpj(visitante.getEmpresa().getCnpj());
            if (empresaExiste == null) {
                throw new EmpresaCadastroException("Empresa com CNPJ", "Empresa com esse CNPJ nao cadastrada.");
            }
        }

        visitante.setDataCadastro(LocalDateTime.now());

        if (visitante.getEmail().contains("@coworking.com.br")) {
            visitante.setTipo(TipoVisitante.FUNCIONARIO);
        } else if (visitante.getEmail().contains("@coworkingadmin.com.br")) {
            visitante.setTipo(TipoVisitante.ADMIN);
        } else {
            visitante.setTipo(TipoVisitante.VISITANTE);
        }

        visitante.setAtivo(true);
        visitante.setSenha(passwordEncoder.encode(visitante.getSenha()));

        visitanteRepository.save(visitante);
    }
}