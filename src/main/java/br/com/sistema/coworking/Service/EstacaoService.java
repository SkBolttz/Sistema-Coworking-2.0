package br.com.sistema.coworking.Service;

import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.sistema.coworking.DTO.Estacao.AtualizarEstacaoDTO;
import br.com.sistema.coworking.Entity.Estacao;
import br.com.sistema.coworking.Entity.Sala;
import br.com.sistema.coworking.Exception.Records.Estacao.AtualizarEstacaoException;
import br.com.sistema.coworking.Exception.Records.Estacao.EstacaoException;
import br.com.sistema.coworking.Repository.EstacaoRepository;
import br.com.sistema.coworking.Repository.SalaRepository;

@Service
public class EstacaoService {

    private final EstacaoRepository estacaoRepository;
    private final SalaRepository salaRepository;

    public EstacaoService(EstacaoRepository estacaoRepository, SalaRepository salaRepository) {
        this.estacaoRepository = estacaoRepository;
        this.salaRepository = salaRepository;
    }

    public void cadastrarEstacao(Estacao estacao) {

        verificarDisponibilidadeSala(estacao.getSala());

        estacao.setAtivo(true);
        estacao.setDisponivel(true);
        estacao.setDataCriacao(LocalDateTime.now());

        estacaoRepository.save(estacao);
    }

    public void atualizarEstacao(AtualizarEstacaoDTO estacao) {

        Estacao estacaoExiste = estacaoRepository.findById(estacao.id())
                .orElseThrow(() -> new AtualizarEstacaoException("Estação não encontrada", ""));

        verificarDisponibilidadeSala(estacao.sala());
        aplicarAtualizacaoSala(estacaoExiste, estacao);

        estacaoRepository.save(estacaoExiste);
    }

    public void ativarEstacao(AtualizarEstacaoDTO estacao) {

        Estacao estacaoExiste = estacaoRepository.findById(estacao.id())
                .orElseThrow(() -> new AtualizarEstacaoException("Estação não encontrada", ""));

        estacaoExiste.setAtivo(true);
        estacaoRepository.save(estacaoExiste);
    }

    public void desativarEstacao(AtualizarEstacaoDTO estacao) {

        Estacao estacaoExiste = estacaoRepository.findById(estacao.id())
                .orElseThrow(() -> new AtualizarEstacaoException("Estação não encontrada", ""));

        estacaoExiste.setAtivo(false);
        estacaoRepository.save(estacaoExiste);
    }

    public Estacao obterEstacao(AtualizarEstacaoDTO estacao) {

        return obterEstacaoId(estacao);
    }

    public Page<Estacao> listarEstacoes(Pageable pageable) {

        return listagemEstacoes(pageable);
    }

    public Page<Estacao> listarEstacoesAtivas(Pageable pageable) {

        return listagemEstacoesAtivas(pageable);
    }

    public Page<Estacao> listarEstacoesInativas(Pageable pageable) {

        return listagemEstacoesInativas(pageable);
    }

    private void verificarDisponibilidadeSala(Sala sala) {

        Sala salaEncontrada = salaRepository.findById(sala.getId())
                .orElseThrow(() -> new EstacaoException("Sala não encontrada", ""));

        if (!salaEncontrada.isDisponivel()) {
            throw new EstacaoException("Sala indisponível no momento, por favor escolha outra sala!", "");
        }
    }

    private void aplicarAtualizacaoSala(Estacao estacaoExiste, AtualizarEstacaoDTO estacao) {

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

        if (estacao.sala() != null) {
            estacaoExiste.setSala(estacao.sala());
        }
    }

    private Estacao obterEstacaoId(AtualizarEstacaoDTO estacao) {

        return estacaoRepository.findById(estacao.id())
                .orElseThrow(() -> new EstacaoException("Estação nao encontrada", ""));
    }

    private Page<Estacao> listagemEstacoes(Pageable pageable) {

        return estacaoRepository.findAll(pageable);
    }

    private Page<Estacao> listagemEstacoesAtivas(Pageable pageable) {

        return estacaoRepository.findByAtivoTrue(pageable);
    }

    private Page<Estacao> listagemEstacoesInativas(Pageable pageable) {

        return estacaoRepository.findByAtivoFalse(pageable);
    }
}
