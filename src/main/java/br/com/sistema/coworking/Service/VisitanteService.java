package br.com.sistema.coworking.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.sistema.coworking.DTO.Visitante.AtualizarVisitante;
import br.com.sistema.coworking.Entity.Visitante;
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
                .orElseThrow(() -> new RuntimeException(
                        "Visitante com CPF " + atualizarVisitante.cpf() + " não encontrado."));

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

        visitanteRepository.save(visitante);
    }

    public void desativarVisitante(AtualizarVisitante atualizarVisitante) {

        Visitante visitante = visitanteRepository.findByCpf(atualizarVisitante.cpf())
                .orElseThrow(() -> new RuntimeException(
                        "Visitante com CPF " + atualizarVisitante.cpf() + " não encontrado."));

        visitante.setAtivo(false);
        visitanteRepository.save(visitante);
    }

    public void ativarVisitante(AtualizarVisitante atualizarVisitante) {

        Visitante visitante = visitanteRepository.findByCpf(atualizarVisitante.cpf())
                .orElseThrow(() -> new RuntimeException(
                        "Visitante com CPF " + atualizarVisitante.cpf() + " não encontrado."));

        visitante.setAtivo(true);
        visitanteRepository.save(visitante);
    }

    public Visitante obterDadosVisitante(AtualizarVisitante dadosVisitante) {
        return visitanteRepository.findByCpf(dadosVisitante.cpf().toString())
                .orElseThrow(() -> new RuntimeException(
                        "Visitante com CPF " + dadosVisitante.cpf() + " não encontrado."));
    }

}
