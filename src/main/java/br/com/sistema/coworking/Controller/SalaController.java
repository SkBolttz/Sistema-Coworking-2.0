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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/sala")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @PostMapping("/criar")
    public ResponseEntity<String> criarSala(@RequestBody @Valid Sala sala) {
        try {
            salaService.criarSala(sala);
            return ResponseEntity.ok("Sala criada com sucesso!");
        } catch (CadastroSalaExecption e) {
            return ResponseEntity.badRequest().body("Erro ao criar sala: " + e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarSala(@RequestBody @Valid AtualizarSalaDTO atualizarSala) {
        try {
            salaService.atualizarSala(atualizarSala);
            return ResponseEntity.ok("Sala atualizada com sucesso!");
        } catch (CadastroSalaExecption e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar sala: " + e.getMessage());
        }
    }

    @PutMapping("/ativar")
    public ResponseEntity<String> ativarSala(@RequestBody @Valid AtualizarSalaDTO atualizarSala) {
        try {
            salaService.ativarSala(atualizarSala);
            return ResponseEntity.ok("Sala ativada com sucesso!");
        } catch (CadastroSalaExecption e) {
            return ResponseEntity.badRequest().body("Erro ao ativar sala: " + e.getMessage());
        }
    }

    @PutMapping("/desativar")
    public ResponseEntity<String> desativarSala(@RequestBody @Valid AtualizarSalaDTO atualizarSala) {
        try {
            salaService.desativarSala(atualizarSala);
            return ResponseEntity.ok("Sala desativada com sucesso!");
        } catch (CadastroSalaExecption e) {
            return ResponseEntity.badRequest().body("Erro ao desativar sala: " + e.getMessage());
        }
    }

    @PostMapping("/dados")
    public ResponseEntity<Sala> obterDadosSala(@RequestBody AtualizarSalaDTO atualizarSala) {
        try {
            Sala sala = salaService.obterDadosSala(atualizarSala);
            return ResponseEntity.ok(sala);
        } catch (CadastroSalaExecption e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listar-todas")
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
