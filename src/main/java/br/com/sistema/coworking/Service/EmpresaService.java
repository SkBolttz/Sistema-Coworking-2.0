package br.com.sistema.coworking.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.sistema.coworking.DTO.Empresa.AtualizarEmpresaDTO;
import br.com.sistema.coworking.Entity.Empresa;
import br.com.sistema.coworking.Entity.Visitante;
import br.com.sistema.coworking.Enum.AprovacaoStatus;
import br.com.sistema.coworking.Exception.Records.Empresa.AtualizarEmpresaException;
import br.com.sistema.coworking.Exception.Records.Empresa.CadastroEmpresaException;
import br.com.sistema.coworking.Repository.EmpresaRepository;
import br.com.sistema.coworking.Repository.VisitanteRepository;
import jakarta.transaction.Transactional;

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

    @Transactional
    public void cadastro(Empresa empresa) {

        Optional<Visitante> visitanteExistente = visitanteRepository.findByCpf(empresa.getResponsavel().getCpf());

        System.out.println(empresa);
        verificarCadastro(empresa, visitanteExistente);
        aplicandoCadastro(empresa, visitanteExistente);

        empresaRepository.save(empresa);
    }

    public void ativar(AtualizarEmpresaDTO empresa) {

        Empresa empresaExiste = empresaRepository.findByCnpj(empresa.cnpj());
        verificarAtivacao(empresa, empresaExiste);

        empresaExiste.setAtivo(true);
        empresaRepository.save(empresaExiste);
    }

    public void desativar(AtualizarEmpresaDTO empresa) {

        Empresa empresaExiste = empresaRepository.findByCnpj(empresa.cnpj());
        verificarDesativacao(empresa, empresaExiste);

        empresaExiste.setAtivo(false);
        empresaRepository.save(empresaExiste);
    }

    public void atualizar(AtualizarEmpresaDTO empresa) {

        Empresa empresaExiste = empresaRepository.findByCnpj(empresa.cnpj());
        verificarAtualizacao(empresa, empresaExiste);
        aplicandoAtualizacao(empresa, empresaExiste);

        empresaRepository.save(empresaExiste);
    }

    public Empresa obter(AtualizarEmpresaDTO empresa) {

        return obterEmpresa(empresa);
    }

    public Page<Empresa> listarEmpresas(Pageable pageable) {
        return empresaRepository.findAll(pageable);
    }

    public Page<Empresa> listarEmpresasAtivas(Pageable pageable) {
        return empresaRepository.findByAtivoTrue(pageable);
    }

    public Page<Empresa> listarEmpresasInativas(Pageable pageable) {
        return empresaRepository.findByAtivoFalse(pageable);
    }

    private void verificarCadastro(Empresa empresa, Optional<Visitante> visitanteExistente) {

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

        if (visitanteExistente == null) {
            throw new CadastroEmpresaException("Visitante com nome",
                    "Visitante com esse nome nao cadastrado.");
        }
    }

    private void aplicandoCadastro(Empresa empresa, Optional<Visitante> visitanteExistente) {

        empresa.setResponsavel(visitanteExistente.get());
        empresa.setAtivo(true);
        empresa.setDataCriacao(LocalDateTime.now());
        empresa.setStatus(AprovacaoStatus.PENDENTE);
        empresa.setEndereco(enderecoService.cadastrarEndereco(empresa.getEndereco()));
    }

    private void verificarAtivacao(AtualizarEmpresaDTO empresa, Empresa empresaExiste) {

        if (empresaExiste == null) {
            throw new AtualizarEmpresaException("Empresa com CNPJ",
                    "Empresa com esse CNPJ nao cadastrada.");
        }
    }

    private void verificarDesativacao(AtualizarEmpresaDTO empresa, Empresa empresaExiste) {

        if (empresaExiste == null) {
            throw new AtualizarEmpresaException("Empresa com CNPJ",
                    "Empresa com esse CNPJ nao cadastrada.");
        }
    }

    private void verificarAtualizacao(AtualizarEmpresaDTO empresa, Empresa empresaExiste) {

        if (empresaExiste == null) {
            throw new AtualizarEmpresaException("Empresa com CNPJ",
                    "Empresa com esse CNPJ nao cadastrada.");
        }

        if (empresa.responsavel() != null) {
            Visitante visitanteExistente = visitanteRepository
                    .findByNomeCompleto(empresa.responsavel().getNomeCompleto());

            if (visitanteExistente == null) {
                throw new AtualizarEmpresaException("Visitante com nome",
                        "Visitante com esse nome nao cadastrado.");
            }
        }

        if (empresaRepository.findByNomeFantasia(empresa.nomeFantasia()) != null) {
            throw new AtualizarEmpresaException("Empresa com nome fantasia",
                    "Empresa com esse nome fantasia ja cadastrada.");
        }

        if (empresaRepository.findByRazaoSocial(empresa.razaoSocial()) != null) {
            throw new AtualizarEmpresaException("Empresa com razao social",
                    "Empresa com essa razao social ja cadastrada.");
        }

        if (empresaRepository.findByEmail(empresa.email()) != null) {
            throw new AtualizarEmpresaException("Empresa com email",
                    "Empresa com esse email ja cadastrada.");
        }

        if (empresaRepository.findByTelefone(empresa.telefone()) != null) {
            throw new AtualizarEmpresaException("Empresa com telefone",
                    "Empresa com esse telefone ja cadastrada.");
        }
    }

    private void aplicandoAtualizacao(AtualizarEmpresaDTO empresa, Empresa empresaExiste) {

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
            empresaExiste.setResponsavel(empresa.responsavel());
        }

        if (empresa.endereco() != null) {
            empresaExiste.setEndereco(enderecoService.cadastrarEndereco(empresa.endereco()));
        }
    }

    private Empresa obterEmpresa(AtualizarEmpresaDTO empresa) {

        return empresaRepository.findByCnpj(empresa.cnpj());
    }

    public Page<Empresa> listagemEmpresas(Pageable pageable) {
        return empresaRepository.findAll(pageable);
    }

    public Page<Empresa> listagemEmpresasAtivas(Pageable pageable) {
        return empresaRepository.findByAtivoTrue(pageable);
    }

    public Page<Empresa> listagemEmpresasInativas(Pageable pageable) {
        return empresaRepository.findByAtivoFalse(pageable);
    }
}
