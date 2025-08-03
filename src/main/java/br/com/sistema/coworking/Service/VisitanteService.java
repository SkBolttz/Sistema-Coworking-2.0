package br.com.sistema.coworking.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import br.com.sistema.coworking.DTO.Visitante.AtualizarVisitante;
import br.com.sistema.coworking.Entity.Visitante;
import br.com.sistema.coworking.Exception.Records.Visitante.AtualizarDadosException;
import br.com.sistema.coworking.Exception.Records.Visitante.VisitanteException;
import br.com.sistema.coworking.Repository.VisitanteRepository;
import jakarta.validation.Valid;

@Service
public class VisitanteService {

    private final VisitanteRepository visitanteRepository;
    private final PasswordEncoder passwordEncoder;

    public VisitanteService(VisitanteRepository visitanteRepository, PasswordEncoder passwordEncoder) {
        this.visitanteRepository = visitanteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void atualizarDados(AtualizarVisitante atualizarVisitante, MultipartFile fotoDocumento) {

        Visitante visitante = visitanteRepository.findByCpf(atualizarVisitante.cpf())
                .orElseThrow(() -> new VisitanteException(
                        "Visitante com CPF " + atualizarVisitante.cpf() + " não encontrado.", ""));

        if (fotoDocumento != null && !fotoDocumento.isEmpty()) {
            try {
                String diretorio = new File("imagens/funcionarios").getAbsolutePath();

                String nomeArquivoSeguro = fotoDocumento.getOriginalFilename()
                        .replaceAll(" ", "_")
                        .replaceAll("\\(", "_")
                        .replaceAll("\\)", "");

                File pasta = new File(diretorio);
                if (!pasta.exists()) {
                    pasta.mkdirs();
                }

                File destino = new File(pasta, nomeArquivoSeguro);
                fotoDocumento.transferTo(destino);

                visitante.setFotoDocumentoUrl(nomeArquivoSeguro);

            } catch (IOException e) {
                throw new RuntimeException("Erro ao salvar a imagem: " + e.getMessage());
            }
        }

        verificarAtualizacao(visitante, atualizarVisitante);
        aplicarAtualizacao(visitante, atualizarVisitante);

        visitanteRepository.save(visitante);
    }

    public void desativarVisitante(AtualizarVisitante atualizarVisitante) {

        Visitante visitante = visitanteRepository.findByCpf(atualizarVisitante.cpf())
                .orElseThrow(() -> new VisitanteException(
                        "Visitante com CPF " + atualizarVisitante.cpf() + " não encontrado.", ""));

        visitante.setAtivo(false);
        visitanteRepository.save(visitante);
    }

    public void ativarVisitante(AtualizarVisitante atualizarVisitante) {

        Visitante visitante = visitanteRepository.findByCpf(atualizarVisitante.cpf())
                .orElseThrow(() -> new VisitanteException(
                        "Visitante com CPF " + atualizarVisitante.cpf() + " não encontrado.", ""));

        visitante.setAtivo(true);
        visitanteRepository.save(visitante);
    }

    public Visitante obterDadosVisitante(AtualizarVisitante dadosVisitante) {

        return obterDados(dadosVisitante);
    }

    public Page<Visitante> listarVisitantes(Pageable pageable) {

        return listagemVisitantes(pageable);
    }

    public Visitante obterDadosPorId(AtualizarVisitante dadosVisitante) {

        return obterDadosId(dadosVisitante);
    }

    private void verificarAtualizacao(Visitante visitante, AtualizarVisitante atualizarVisitante) {

        if (atualizarVisitante.email() != null) {
            visitanteRepository.findByEmail(atualizarVisitante.email()).ifPresent(visitanteExistente -> {
                if (!visitanteExistente.getCpf().equals(visitante.getCpf())) {
                    throw new AtualizarDadosException("Email ja cadastrado", "O email fornecido ja esta em uso.");
                }
            });
        }
    }

    private void aplicarAtualizacao(Visitante visitante, AtualizarVisitante atualizarVisitante) {

        if (atualizarVisitante.email() != null)
            visitante.setEmail(atualizarVisitante.email());
        if (atualizarVisitante.senha() != null)
            visitante.setSenha(passwordEncoder.encode(atualizarVisitante.senha()));
        if (atualizarVisitante.telefone() != null)
            visitante.setTelefone(atualizarVisitante.telefone());
        if (atualizarVisitante.observacao() != null)
            visitante.setObservacao(atualizarVisitante.observacao());
        if (atualizarVisitante.fotoDocumento() != null)
            visitante.setFotoDocumentoUrl(atualizarVisitante.fotoDocumento());
        if (atualizarVisitante.nomeCompleto() != null)
            visitante.setNomeCompleto(atualizarVisitante.nomeCompleto());
    }

    private Visitante obterDados(AtualizarVisitante dadosVisitante) {

        return visitanteRepository.findByCpf(dadosVisitante.cpf()).orElseThrow(() -> new VisitanteException(
                "Visitante com CPF " + dadosVisitante.cpf() + " não encontrado.", ""));
    }

    private Visitante obterDadosId(AtualizarVisitante dadosVisitante) {

        return visitanteRepository.findById(dadosVisitante.id()).orElseThrow(() -> new VisitanteException(
                "Visitante com ID " + dadosVisitante.id() + " não encontrado.", ""));
    }

    private Page<Visitante> listagemVisitantes(Pageable pageable) {

        return visitanteRepository.findAll(pageable);
    }

    public Boolean verificarEmail(@Valid AtualizarVisitante atualizarVisitante) {

        return visitanteRepository.findByCpf(atualizarVisitante.cpf())
                .stream()
                .anyMatch(e -> e.getEmail().contains("@coworkingadmin.com.br")
                        || e.getEmail().contains("@coworking.com.br"));
    }

    public Page<Visitante> listarFuncionario(Pageable pageable) {
        return visitanteRepository.findByEmailContainingFuncionario("coworking.com.br", "coworkingadmin.com.br",
                pageable);
    }

    public void trocarSenha(AtualizarVisitante atualizarVisitante) {
        Visitante visitanteExiste = visitanteRepository.findById(atualizarVisitante.id())
                .orElseThrow(() -> new VisitanteException(
                        "Visitante com CPF " + atualizarVisitante.cpf() + " não encontrado.", ""));

        if (passwordEncoder.matches(atualizarVisitante.senha(), visitanteExiste.getPassword())) {
            if (atualizarVisitante.novaSenha().equals(atualizarVisitante.confirmaSenha())) {
                visitanteExiste.setSenha(passwordEncoder.encode(atualizarVisitante.novaSenha()));
                visitanteRepository.save(visitanteExiste);
            } else {
                throw new VisitanteException("Senhas nao conferem", "");
            }
        } else {
            throw new VisitanteException("Senha atual incorreta", "");
        }
    }

    public List<Visitante> listarVisitantesDisponiveis() {
        return visitanteRepository.findByEmpresaIsNull();
    }
}
