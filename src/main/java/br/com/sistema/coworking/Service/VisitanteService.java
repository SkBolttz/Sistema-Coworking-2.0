package br.com.sistema.coworking.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.sistema.coworking.DTO.Visitante.AtualizarVisitante;
import br.com.sistema.coworking.Entity.Visitante;
import br.com.sistema.coworking.Exception.Records.Visitante.AtualizarDadosException;
import br.com.sistema.coworking.Exception.Records.Visitante.VisitanteException;
import br.com.sistema.coworking.Repository.VisitanteRepository;

@Service
public class VisitanteService {

    private final VisitanteRepository visitanteRepository;
    private final PasswordEncoder passwordEncoder;

    public VisitanteService(VisitanteRepository visitanteRepository, PasswordEncoder passwordEncoder) {
        this.visitanteRepository = visitanteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void atualizarDados(AtualizarVisitante atualizarVisitante) {

        Visitante visitante = visitanteRepository.findByCpf(atualizarVisitante.cpf())
                .orElseThrow(() -> new VisitanteException(
                        "Visitante com CPF " + atualizarVisitante.cpf() + " não encontrado.", ""));

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

    private Page<Visitante> listagemVisitantes(Pageable pageable) {

        return visitanteRepository.findAll(pageable);
    }
}
