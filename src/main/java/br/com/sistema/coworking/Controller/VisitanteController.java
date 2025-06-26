package br.com.sistema.coworking.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RequestMapping("/visitante")
@RestController
@Tag(name = "Visitante", description = "Controller responsável por gerenciar as informações dos visitantes.")
public class VisitanteController {

    private final VisitanteService visitanteService;

    public VisitanteController(VisitanteService visitanteService) {
        this.visitanteService = visitanteService;
    }

    @PutMapping("/atualizar")
    @Operation(summary = "Atualizar dados do visitante", description = "Atualiza os dados do visitante com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "atualizarVisitante", description = "Informações do visitante para atualizar."),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar dados."),
    })
    public ResponseEntity<String> atualizarVisitante(@RequestBody @Valid AtualizarVisitante atualizarVisitante) {
        try {
            visitanteService.atualizarDados(atualizarVisitante);
            return ResponseEntity.status(HttpStatus.OK).body("Dados atualizados com sucesso!");
        } catch (AtualizarDadosException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar dados: " + ex.getDetalhe());
        }
    }

    @PutMapping("/desativar")
    @Operation(summary = "Desativar visitante", description = "Desativa o visitante com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "atualizarVisitante", description = "Informações do visitante para desativar."),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visitante desativado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao desativar visitante."),
    })
    public ResponseEntity<String> desativarVisitante(@RequestBody @Valid AtualizarVisitante atualizarVisitante) {
        try {
            visitanteService.desativarVisitante(atualizarVisitante);
            return ResponseEntity.status(HttpStatus.OK).body("Visitante desativado com sucesso!");
        } catch (AtualizarDadosException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao desativar visitante: " + ex.getDetalhe());
        }
    }

    @PutMapping("/ativar")
    @Operation(summary = "Ativar visitante", description = "Ativa o visitante com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "atualizarVisitante", description = "Informações do visitante para ativar."),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visitante ativo com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao ativar visitante."),
    })
    public ResponseEntity<String> ativarVisitante(@RequestBody @Valid AtualizarVisitante atualizarVisitante) {
        try {
            visitanteService.ativarVisitante(atualizarVisitante);
            return ResponseEntity.status(HttpStatus.OK).body("Visitante ativo com sucesso!");
        } catch (AtualizarDadosException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao ativar visitante: " + ex.getDetalhe());
        }
    }

    @PostMapping("/dados")
    @Operation(summary = "Obter dados do visitante", description = "Obtem os dados do visitante com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "atualizarVisitante", description = "Informações do visitante para obter."),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados obtidos com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao obter dados."),
    })
    public ResponseEntity<Visitante> obterDadosVisitante(@RequestBody AtualizarVisitante dadosVisitante) {
        try {
            Visitante visitante = visitanteService.obterDadosVisitante(dadosVisitante);
            return ResponseEntity.status(HttpStatus.OK).body(visitante);
        } catch (DadosException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar visitantes", description = "Lista os visitantes com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "pageable", description = "Parâmetros de paginação."),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visitantes listados com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao listar visitantes."),
    })
    public ResponseEntity<Page<Visitante>> listarVisitantes(
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(visitanteService.listarVisitantes(pageable));
        } catch (DadosException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
