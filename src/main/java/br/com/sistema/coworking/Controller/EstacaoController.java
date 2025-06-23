package br.com.sistema.coworking.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sistema.coworking.DTO.Estacao.AtualizarEstacaoDTO;
import br.com.sistema.coworking.Entity.Estacao;
import br.com.sistema.coworking.Exception.Records.Estacao.AtualizarEstacaoException;
import br.com.sistema.coworking.Exception.Records.Estacao.CadastroEstacaoException;
import br.com.sistema.coworking.Service.EstacaoService;
import jakarta.validation.Valid;

@RequestMapping("/estacao")
@RestController
public class EstacaoController {

    private final EstacaoService estacaoService;

    public EstacaoController(EstacaoService estacaoService) {
        this.estacaoService = estacaoService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarEstacao(@RequestBody @Valid Estacao estacao) {

        try {
            estacaoService.cadastrarEstacao(estacao);
            return ResponseEntity.ok("Estacao cadastrada com sucesso!");
        } catch (CadastroEstacaoException e) {
            return ResponseEntity.status(400).body("Erro ao cadastrar estacao: " + e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarEstacao(@RequestBody @Valid AtualizarEstacaoDTO estacao) {

        try {
            estacaoService.atualizarEstacao(estacao);
            return ResponseEntity.ok("Estacao atualizada com sucesso!");
        } catch (AtualizarEstacaoException e) {
            return ResponseEntity.status(400).body("Erro ao atualizar estacao: " + e.getMessage());
        }
    }

    @PutMapping("/ativar")
    public ResponseEntity<String> ativarEstacao(@RequestBody @Valid AtualizarEstacaoDTO estacao) {

        try {
            estacaoService.ativarEstacao(estacao);
            return ResponseEntity.ok("Estacao ativada com sucesso!");
        } catch (AtualizarEstacaoException e) {
            return ResponseEntity.status(400).body("Erro ao ativar estacao: " + e.getMessage());
        }
    }

    @PutMapping("/desativar")
    public ResponseEntity<String> desativarEstacao(@RequestBody @Valid AtualizarEstacaoDTO estacao) {

        try {
            estacaoService.desativarEstacao(estacao);
            return ResponseEntity.ok("Estacao desativada com sucesso!");
        } catch (AtualizarEstacaoException e) {
            return ResponseEntity.status(400).body("Erro ao desativar estacao: " + e.getMessage());
        }
    }

    @PostMapping("/obter")
    public ResponseEntity<String> obterEstacao(@RequestBody @Valid AtualizarEstacaoDTO estacao) {

        try {
            return ResponseEntity.status(200).body(estacaoService.obterEstacao(estacao).toString());
        } catch (AtualizarEstacaoException e) {
            return ResponseEntity.status(400).body("Erro ao obter estacao: " + e.getMessage());
        }
    }

    @GetMapping("/listar-todas")
    public ResponseEntity<List<Estacao>> listarEstacoes() {
        try {
            return ResponseEntity.status(200).body(estacaoService.listarEstacoes());
        } catch (AtualizarEstacaoException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/listar-ativas")
    public ResponseEntity<List<Estacao>> listarEstacoesAtivas() {
        try {
            return ResponseEntity.status(200).body(estacaoService.listarEstacoesAtivas());
        } catch (AtualizarEstacaoException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/listar-inativas")
    public ResponseEntity<List<Estacao>> listarEstacoesInativas() {
        try {
            return ResponseEntity.status(200).body(estacaoService.listarEstacoesInativas());
        } catch (AtualizarEstacaoException e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}
