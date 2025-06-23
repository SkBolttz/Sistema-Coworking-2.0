package br.com.sistema.coworking.Service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import br.com.sistema.coworking.DTO.Estacao.AtualizarEstacaoDTO;
import br.com.sistema.coworking.Entity.Estacao;
import br.com.sistema.coworking.Repository.EstacaoRepository;

@Service
public class EstacaoService {

    private final EstacaoRepository estacaoRepository;

    public EstacaoService(EstacaoRepository estacaoRepository) {
        this.estacaoRepository = estacaoRepository;
    }

    public void cadastrarEstacao(Estacao estacao) {

        estacao.setAtivo(true);
        estacao.setDisponivel(true);
        estacao.setDataCriacao(LocalDateTime.now());

        estacaoRepository.save(estacao);
    }

    public void atualizarEstacao(AtualizarEstacaoDTO estacao) {

        Estacao estacaoExiste = estacaoRepository.findById(estacao.id())
                .orElseThrow(() -> new RuntimeException("Estação não encontrada"));

        if (estacao.identificador() != null) {
            estacaoExiste.setIdentificacao(estacao.identificador());
        }

        if (estacao.descricao() != null) {
            estacaoExiste.setDescricao(estacao.descricao());
        }

        if (estacao.disponivel() != null) {
            estacaoExiste.setDisponivel(estacao.disponivel());
        }

        if (estacao.monitor() != null) {
            estacaoExiste.setMonitor(estacao.monitor());
        }

        if (estacao.tecladoMouse() != null) {
            estacaoExiste.setTecladoMouse(estacao.tecladoMouse());
        }

        if (estacao.cadeiraErgonomica() != null) {
            estacaoExiste.setCadeiraErgonomica(estacao.cadeiraErgonomica());
        }

        if (estacao.fotoUrl() != null) {
            estacaoExiste.setFotoUrl(estacao.fotoUrl());
        }

        estacaoRepository.save(estacaoExiste);
    }

    public void ativarEstacao(AtualizarEstacaoDTO estacao) {

        Estacao estacaoExiste = estacaoRepository.findById(estacao.id())
                .orElseThrow(() -> new RuntimeException("Estação não encontrada"));

        estacaoExiste.setAtivo(true);
        estacaoRepository.save(estacaoExiste);
    }

    public void desativarEstacao(AtualizarEstacaoDTO estacao) {

        Estacao estacaoExiste = estacaoRepository.findById(estacao.id())
                .orElseThrow(() -> new RuntimeException("Estação não encontrada"));

        estacaoExiste.setAtivo(false);
        estacaoRepository.save(estacaoExiste);
    }

    public Object obterEstacao(AtualizarEstacaoDTO estacao) {

        return estacaoRepository.findById(estacao.id())
                .stream()
                .toList();
    }

    public List<Estacao> listarEstacoes() {

        return estacaoRepository.findAll()
                .stream()
                .toList();
    }

    public List<Estacao> listarEstacoesAtivas() {

        return estacaoRepository.findAll()
                .stream()
                .filter(e -> e.isAtivo() == true)
                .toList();
    }

    public List<Estacao> listarEstacoesInativas() {

        return estacaoRepository.findAll()
                .stream()
                .filter(e -> e.isAtivo() == false)
                .toList();
    }
}
