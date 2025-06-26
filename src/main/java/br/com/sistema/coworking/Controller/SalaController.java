package br.com.sistema.coworking.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.sistema.coworking.DTO.Sala.AtualizarSalaDTO;
import br.com.sistema.coworking.Entity.Sala;
import br.com.sistema.coworking.Exception.Records.Sala.CadastroSalaExecption;
import br.com.sistema.coworking.Service.SalaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/sala")
@Tag(name = "Sala", description = "Controller responsável por gerenciar as informações das salas.")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar sala", description = "Cria uma nova sala com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "sala", description = "Informações da sala para criar."),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao criar sala."),
    })
    public ResponseEntity<String> criarSala(@RequestBody @Valid Sala sala) {
        try {
            salaService.criarSala(sala);
            return ResponseEntity.ok("Sala criada com sucesso!");
        } catch (CadastroSalaExecption e) {
            return ResponseEntity.badRequest().body("Erro ao criar sala: " + e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    @Operation(summary = "Atualizar sala", description = "Atualiza uma sala existente com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "atualizarSala", description = "Informações da sala para atualizar."),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar sala."),
    })
    public ResponseEntity<String> atualizarSala(@RequestBody @Valid AtualizarSalaDTO atualizarSala) {
        try {
            salaService.atualizarSala(atualizarSala);
            return ResponseEntity.ok("Sala atualizada com sucesso!");
        } catch (CadastroSalaExecption e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar sala: " + e.getMessage());
        }
    }

    @PutMapping("/ativar")
    @Operation(summary = "Ativar sala", description = "Ativa uma sala existente com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "atualizarSala", description = "Informações da sala para ativar."),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala ativada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao ativar sala."),
    })
    public ResponseEntity<String> ativarSala(@RequestBody @Valid AtualizarSalaDTO atualizarSala) {
        try {
            salaService.ativarSala(atualizarSala);
            return ResponseEntity.ok("Sala ativada com sucesso!");
        } catch (CadastroSalaExecption e) {
            return ResponseEntity.badRequest().body("Erro ao ativar sala: " + e.getMessage());
        }
    }

    @PutMapping("/desativar")
    @Operation(summary = "Desativar sala", description = "Desativa uma sala existente com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "atualizarSala", description = "Informações da sala para desativar."),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala desativada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao desativar sala."),
    })
    public ResponseEntity<String> desativarSala(@RequestBody @Valid AtualizarSalaDTO atualizarSala) {
        try {
            salaService.desativarSala(atualizarSala);
            return ResponseEntity.ok("Sala desativada com sucesso!");
        } catch (CadastroSalaExecption e) {
            return ResponseEntity.badRequest().body("Erro ao desativar sala: " + e.getMessage());
        }
    }

    @PostMapping("/dados")
    @Operation(summary = "Obter dados da sala", description = "Obtem os dados da sala com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "atualizarSala", description = "Informações da sala para obter."),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados obtidos com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao obter dados."),
    })
    public ResponseEntity<Sala> obterDadosSala(@RequestBody AtualizarSalaDTO atualizarSala) {
        try {
            Sala sala = salaService.obterDadosSala(atualizarSala);
            return ResponseEntity.ok(sala);
        } catch (CadastroSalaExecption e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listar-todas")
    @Operation(summary = "Listar todas as salas", description = "Lista todas as salas com base nas informações fornecidas no corpo da requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salas obtidas com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao obter salas."),
    })
    public ResponseEntity<Page<Sala>> listarSalas(
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<Sala> salas = salaService.listarSalas(pageable);
            return ResponseEntity.ok(salas);
        } catch (CadastroSalaExecption e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listar-disponiveis")
    @Operation(summary = "Listar salas disponíveis", description = "Lista as salas disponíveis com base nas informações fornecidas no corpo da requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salas obtidas com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao obter salas."),
    })
    public ResponseEntity<Page<Sala>> listarSalasDisponiveis(
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<Sala> salasDisponiveis = salaService.listarSalasDisponiveis(pageable);
            return ResponseEntity.ok(salasDisponiveis);
        } catch (CadastroSalaExecption e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listar-indisponiveis")
    @Operation(summary = "Listar salas indisponíveis", description = "Lista as salas indisponíveis com base nas informações fornecidas no corpo da requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salas obtidas com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao obter salas."),
    })
    public ResponseEntity<Page<Sala>> listarSalasIndisponiveis(
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<Sala> salasIndisponiveis = salaService.listarSalasIndisponiveis(pageable);
            return ResponseEntity.ok(salasIndisponiveis);
        } catch (CadastroSalaExecption e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
