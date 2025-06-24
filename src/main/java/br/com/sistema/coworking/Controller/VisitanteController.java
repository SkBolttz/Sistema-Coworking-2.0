package br.com.sistema.coworking.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sistema.coworking.DTO.Visitante.AtualizarVisitante;
import br.com.sistema.coworking.Entity.Visitante;
import br.com.sistema.coworking.Exception.Records.Visitante.AtualizarDadosException;
import br.com.sistema.coworking.Exception.Records.Visitante.DadosException;
import br.com.sistema.coworking.Service.VisitanteService;
import jakarta.validation.Valid;

@RequestMapping("/visitante")
@RestController
public class VisitanteController {

    private final VisitanteService visitanteService;

    public VisitanteController(VisitanteService visitanteService) {
        this.visitanteService = visitanteService;
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarVisitante(@RequestBody @Valid AtualizarVisitante atualizarVisitante) {
        try {
            visitanteService.atualizarDados(atualizarVisitante);
            return ResponseEntity.status(200).body("Dados atualizados com sucesso!");
        } catch (AtualizarDadosException ex) {
            return ResponseEntity.status(400).body("Erro ao atualizar dados: " + ex.getDetalhe());
        }
    }

    @PutMapping("/desativar")
    public ResponseEntity<String> desativarVisitante(@RequestBody @Valid AtualizarVisitante atualizarVisitante) {
        try {
            visitanteService.desativarVisitante(atualizarVisitante);
            return ResponseEntity.status(200).body("Visitante desativado com sucesso!");
        } catch (AtualizarDadosException ex) {
            return ResponseEntity.status(400).body("Erro ao desativar visitante: " + ex.getDetalhe());
        }
    }

    @PutMapping("/ativar")
    public ResponseEntity<String> ativarVisitante(@RequestBody @Valid AtualizarVisitante atualizarVisitante) {
        try {
            visitanteService.ativarVisitante(atualizarVisitante);
            return ResponseEntity.status(200).body("Visitante ativo com sucesso!");
        } catch (AtualizarDadosException ex) {
            return ResponseEntity.status(400).body("Erro ao ativar visitante: " + ex.getDetalhe());
        }
    }

    @PostMapping("/dados")
    public ResponseEntity<Visitante> obterDadosVisitante(@RequestBody AtualizarVisitante dadosVisitante) {
        try {
            Visitante visitante = visitanteService.obterDadosVisitante(dadosVisitante);
            return ResponseEntity.ok(visitante);
        } catch (DadosException e) {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Visitante>> listarVisitantes() {
        try {
            return ResponseEntity.status(200).body(visitanteService.listarVisitantes());
        } catch (DadosException e) {
            return ResponseEntity.status(400).build();
        }
    }
}
