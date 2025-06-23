package br.com.sistema.coworking.Service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import br.com.sistema.coworking.DTO.Empresa.AtualizarEmpresaDTO;
import br.com.sistema.coworking.Entity.Empresa;
import br.com.sistema.coworking.Entity.Visitante;
import br.com.sistema.coworking.Enum.AprovacaoStatus;
import br.com.sistema.coworking.Exception.Records.Empresa.CadastroEmpresaException;
import br.com.sistema.coworking.Repository.EmpresaRepository;
import br.com.sistema.coworking.Repository.VisitanteRepository;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final EnderecoService enderecoService;
    private final VisitanteRepository visitanteRepository;

    public EmpresaService(EmpresaRepository empresaRepository, EnderecoService enderecoService,
            VisitanteRepository visitanteRepository) {
        this.empresaRepository = empresaRepository;
        this.enderecoService = enderecoService;
        this.visitanteRepository = visitanteRepository;
    }

    public void cadastro(Empresa empresa) {

        Empresa nomeFantasia = empresaRepository.findByNomeFantasia(empresa.getNomeFantasia());
        Empresa razaoSocial = empresaRepository.findByRazaoSocial(empresa.getRazaoSocial());
        Empresa cnpj = empresaRepository.findByCnpj(empresa.getCnpj());
        Empresa email = empresaRepository.findByEmail(empresa.getEmail());
        Empresa telefone = empresaRepository.findByTelefone(empresa.getTelefone());

        if (nomeFantasia != null) {
            throw new CadastroEmpresaException("Empresa com nome fantasia",
                    "Empresa com esse nome fantasia ja cadastrada.");
        } else if (razaoSocial != null) {
            throw new CadastroEmpresaException("Empresa com razao social",
                    "Empresa com essa razao social ja cadastrada.");
        } else if (cnpj != null) {
            throw new CadastroEmpresaException("Empresa com CNPJ",
                    "Empresa com esse CNPJ ja cadastrada.");
        } else if (email != null) {
            throw new CadastroEmpresaException("Empresa com email",
                    "Empresa com esse email ja cadastrada.");
        } else if (telefone != null) {
            throw new CadastroEmpresaException("Empresa com telefone",
                    "Empresa com esse telefone ja cadastrada.");
        }

        Visitante visitanteExistente = visitanteRepository
                .findByNomeCompleto(empresa.getResponsavel().getNomeCompleto());

        if (visitanteExistente == null) {
            throw new CadastroEmpresaException("Visitante com nome",
                    "Visitante com esse nome nao cadastrado.");
        }

        empresa.setResponsavel(visitanteExistente);
        empresa.setAtivo(true);
        empresa.setDataCriacao(LocalDateTime.now());
        empresa.setStatus(AprovacaoStatus.PENDENTE);
        empresa.setEndereco(enderecoService.cadastrarEndereco(empresa.getEndereco()));
        empresaRepository.save(empresa);
    }

    public void ativar(AtualizarEmpresaDTO empresa) {

        Empresa empresaExiste = empresaRepository.findByCnpj(empresa.cnpj());

        if (empresaExiste == null) {
            throw new CadastroEmpresaException("Empresa com CNPJ",
                    "Empresa com esse CNPJ nao cadastrada.");
        }

        empresaExiste.setAtivo(true);
        empresaRepository.save(empresaExiste);
    }

    public void desativar(AtualizarEmpresaDTO empresa) {

        System.out.println(empresa.cnpj());
        Empresa empresaExiste = empresaRepository.findByCnpj(empresa.cnpj());

        if (empresaExiste == null) {
            throw new CadastroEmpresaException("Empresa com CNPJ",
                    "Empresa com esse CNPJ nao cadastrada.");
        }

        empresaExiste.setAtivo(false);
        empresaRepository.save(empresaExiste);
    }

    public void atualizar(AtualizarEmpresaDTO empresa) {

        Empresa empresaExiste = empresaRepository.findByCnpj(empresa.cnpj());

        if (empresaExiste == null) {
            throw new CadastroEmpresaException("Empresa com CNPJ",
                    "Empresa com esse CNPJ nao cadastrada.");
        }

        if (empresa.nomeFantasia() != null) {
            empresaExiste.setNomeFantasia(empresa.nomeFantasia());
        }

        if (empresa.razaoSocial() != null) {
            empresaExiste.setRazaoSocial(empresa.razaoSocial());
        }

        if (empresa.email() != null) {
            empresaExiste.setEmail(empresa.email());
        }

        if (empresa.telefone() != null) {
            empresaExiste.setTelefone(empresa.telefone());
        }

        if (empresa.ramo() != null) {
            empresaExiste.setRamo(empresa.ramo());
        }

        if (empresa.responsavel() != null) {
            Visitante visitanteExistente = visitanteRepository
                    .findByNomeCompleto(empresa.responsavel().getNomeCompleto());

            if (visitanteExistente == null) {
                throw new CadastroEmpresaException("Visitante com nome",
                        "Visitante com esse nome nao cadastrado.");
            }

            empresaExiste.setResponsavel(visitanteExistente);
        }

        if (empresa.endereco() != null) {
            empresaExiste.setEndereco(enderecoService.cadastrarEndereco(empresa.endereco()));
        }

        empresaRepository.save(empresaExiste);

    }

    public String obter(AtualizarEmpresaDTO empresa) {
        return empresaRepository.findByCnpj(empresa.cnpj()).toString();
    }

    public List<Empresa> listarEmpresas() {
        return empresaRepository.findAll()
                .stream()
                .toList();
    }

    public List<Empresa> listarEmpresasAtivas() {

        return empresaRepository.findAll()
                .stream()
                .filter(e -> e.isAtivo() == true)
                .toList();
    }

    public List<Empresa> listarEmpresasInativas() {

        return empresaRepository.findAll()
                .stream()
                .filter(e -> e.isAtivo() == false)
                .toList();
    }
}
