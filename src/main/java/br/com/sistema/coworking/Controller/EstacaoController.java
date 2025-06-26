package br.com.sistema.coworking.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RequestMapping("/estacao")
@RestController
@Tag(name = "Estacao", description = "Controller responsável por gerenciar as informações das estacoes.")
public class EstacaoController {

    private final EstacaoService estacaoService;

    public EstacaoController(EstacaoService estacaoService) {
        this.estacaoService = estacaoService;
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar estacao", description = "Cria uma nova estacao com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "estacao", description = "Informações da estacao para criar.")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estacao cadastrada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar estacao.")
    })
    public ResponseEntity<String> cadastrarEstacao(@RequestBody @Valid Estacao estacao) {

        try {
            estacaoService.cadastrarEstacao(estacao);
            return ResponseEntity.ok("Estacao cadastrada com sucesso!");
        } catch (CadastroEstacaoException e) {
            return ResponseEntity.status(400).body("Erro ao cadastrar estacao: " + e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    @Operation(summary = "Atualizar estacao", description = "Atualiza uma estacao com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "estacao", description = "Informações da estacao para atualizar.")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estacao atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar estacao.")
    })
    public ResponseEntity<String> atualizarEstacao(@RequestBody @Valid AtualizarEstacaoDTO estacao) {

        try {
            estacaoService.atualizarEstacao(estacao);
            return ResponseEntity.ok("Estacao atualizada com sucesso!");
        } catch (AtualizarEstacaoException e) {
            return ResponseEntity.status(400).body("Erro ao atualizar estacao: " + e.getMessage());
        }
    }

    @PutMapping("/ativar")
    @Operation(summary = "Ativar estacao", description = "Ativa uma estacao com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "estacao", description = "Informações da estacao para ativar.")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estacao ativada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao ativar estacao.")
    })
    public ResponseEntity<String> ativarEstacao(@RequestBody @Valid AtualizarEstacaoDTO estacao) {

        try {
            estacaoService.ativarEstacao(estacao);
            return ResponseEntity.ok("Estacao ativada com sucesso!");
        } catch (AtualizarEstacaoException e) {
            return ResponseEntity.status(400).body("Erro ao ativar estacao: " + e.getMessage());
        }
    }

    @PutMapping("/desativar")
    @Operation(summary = "Desativar estacao", description = "Desativa uma estacao com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "estacao", description = "Informações da estacao para desativar.")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estacao desativada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao desativar estacao.")
    })
    public ResponseEntity<String> desativarEstacao(@RequestBody @Valid AtualizarEstacaoDTO estacao) {

        try {
            estacaoService.desativarEstacao(estacao);
            return ResponseEntity.ok("Estacao desativada com sucesso!");
        } catch (AtualizarEstacaoException e) {
            return ResponseEntity.status(400).body("Erro ao desativar estacao: " + e.getMessage());
        }
    }

    @PostMapping("/obter")
    @Operation(summary = "Obter estacao", description = "Obtem uma estacao com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "estacao", description = "Informações da estacao para obter.")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estacao obtida com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao obter estacao.")
    })
    public ResponseEntity<String> obterEstacao(@RequestBody @Valid AtualizarEstacaoDTO estacao) {

        try {
            return ResponseEntity.status(200).body(estacaoService.obterEstacao(estacao).toString());
        } catch (AtualizarEstacaoException e) {
            return ResponseEntity.status(400).body("Erro ao obter estacao: " + e.getMessage());
        }
    }

    @GetMapping("/listar-todas")
    @Operation(summary = "Listar todas as estacoes", description = "Lista todas as estacoes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estacoes obtidas com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao obter estacoes."),
    })
    public ResponseEntity<Page<Estacao>> listarEstacoes(
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            return ResponseEntity.ok(estacaoService.listarEstacoes(pageable));
        } catch (AtualizarEstacaoException e) {
            return ResponseEntity.status(400).body(Page.empty());
        }
    }

    @GetMapping("/listar-ativas")
    @Operation(summary = "Listar todas as estacoes ativas", description = "Lista todas as estacoes ativas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estacoes ativas obtidas com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao obter estacoes ativas."),
    })
    public ResponseEntity<Page<Estacao>> listarEstacoesAtivas(
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            return ResponseEntity.ok(estacaoService.listarEstacoesAtivas(pageable));
        } catch (AtualizarEstacaoException e) {
            return ResponseEntity.status(400).body(Page.empty());
        }
    }

    @GetMapping("/listar-inativas")
    @Operation(summary = "Listar todas as estacoes inativas", description = "Lista todas as estacoes inativas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estacoes inativas obtidas com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao obter estacoes inativas."),
    })
    public ResponseEntity<Page<Estacao>> listarEstacoesInativas(
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            return ResponseEntity.ok(estacaoService.listarEstacoesInativas(pageable));
        } catch (AtualizarEstacaoException e) {
            return ResponseEntity.status(400).body(Page.empty());
        }
    }
}

