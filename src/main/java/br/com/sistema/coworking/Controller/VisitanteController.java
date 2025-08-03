package br.com.sistema.coworking.Controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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

    @PutMapping(value = "/atualizar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Atualizar dados do visitante", description = "Atualiza os dados do visitante com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "atualizarVisitante", description = "Informações do visitante para atualizar."),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar dados."),
    })
    public ResponseEntity<String> atualizarVisitante(
            @RequestPart("atualizarVisitante") AtualizarVisitante atualizarVisitante,
            @RequestPart(value = "fotoDocumento", required = false) MultipartFile fotoDocumento) {
        try {
            visitanteService.atualizarDados(atualizarVisitante, fotoDocumento);
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

    @PostMapping("/verificarEmail")
    @Operation(summary = "Verificar email", description = "Verifica se o email fornecido existe na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email encontrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao verificar email."),
    })
    public ResponseEntity<Boolean> verificarEmail(@RequestBody @Valid AtualizarVisitante atualizarVisitante) {
        boolean existe = visitanteService.verificarEmail(atualizarVisitante);
        return ResponseEntity.status(HttpStatus.OK).body(existe);
    }

    @GetMapping("/listar/funcionario")
    @Operation(summary = "Listar funcionarios", description = "Lista os funcionarios com base nas informações fornecidas no corpo da requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionarios listados com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao listar funcionarios."),
    })
    public ResponseEntity<Page<Visitante>> listarFuncionario(
            Pageable pageable) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(visitanteService.listarFuncionario(pageable));
        } catch (DadosException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/obter-por-id")
    @Operation(summary = "Obter dados do visitante", description = "Obtem os dados do visitante com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "atualizarVisitante", description = "Informações do visitante para obter."),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados obtidos com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao obter dados."),
    })
    public ResponseEntity<Visitante> obterDadosPorId(@RequestBody AtualizarVisitante dadosVisitante) {
        try {
            Visitante visitante = visitanteService.obterDadosPorId(dadosVisitante);
            return ResponseEntity.status(HttpStatus.OK).body(visitante);
        } catch (DadosException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/trocar-senha")
    @Operation(summary = "Trocar senha", description = "Troca a senha do visitante com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "atualizarVisitante", description = "Informações do visitante para trocar senha."),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha trocada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao trocar senha."),
    })
    public ResponseEntity<?> trocarSenha(@RequestBody @Valid AtualizarVisitante atualizarVisitante) {
        try {
            visitanteService.trocarSenha(atualizarVisitante);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (DadosException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/listar-visitantes-disponiveis")
    @Operation(summary = "Listar visitantes", description = "Lista os visitantes com base nas informações fornecidas no corpo da requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visitantes listados com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao listar visitantes."),
    })
    public ResponseEntity<List<Visitante>> listarVisitantesDisponiveis() {
        try {
            return ResponseEntity.ok(visitanteService.listarVisitantesDisponiveis());
        } catch (DadosException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
