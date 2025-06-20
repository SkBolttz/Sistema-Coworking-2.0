package br.com.sistema.coworking.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            return ResponseEntity.status(200).body("Sala criada com sucesso!");
        } catch (CadastroSalaExecption e) {
            return ResponseEntity.status(400).body("Erro ao criar sala: " + e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarSala(@RequestBody @Valid AtualizarSalaDTO atualizarSala) {

        try {
            salaService.atualizarSala(atualizarSala);
            return ResponseEntity.status(200).body("Sala atualizada com sucesso!");
        } catch (CadastroSalaExecption e) {
            return ResponseEntity.status(400).body("Erro ao atualizar sala: " + e.getMessage());
        }
    }

    @PutMapping("/ativar")
    public ResponseEntity<String> ativarSala(@RequestBody @Valid AtualizarSalaDTO atualizarSala) {

        try {
            salaService.ativarSala(atualizarSala);
            return ResponseEntity.status(200).body("Sala ativada com sucesso!");
        } catch (CadastroSalaExecption e) {
            return ResponseEntity.status(400).body("Erro ao ativar sala: " + e.getMessage());
        }
    }

    @PutMapping("/desativar")
    public ResponseEntity<String> desativarSala(@RequestBody @Valid AtualizarSalaDTO atualizarSala) {

        try {
            salaService.desativarSala(atualizarSala);
            return ResponseEntity.status(200).body("Sala desativada com sucesso!");
        } catch (CadastroSalaExecption e) {
            return ResponseEntity.status(400).body("Erro ao desativar sala: " + e.getMessage());
        }
    }

    @PostMapping("/dados")
    public ResponseEntity<Sala> obterDadosSala(@RequestBody AtualizarSalaDTO atualizarSala) {
        try {
            Sala sala = salaService.obterDadosSala(atualizarSala);
            return ResponseEntity.ok(sala);
        } catch (CadastroSalaExecption e) {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/listar-todas")
    public ResponseEntity<List<Sala>> listarSalas() {
        try {
            return ResponseEntity.status(200).body(salaService.listarSalas());
        } catch (CadastroSalaExecption e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/listar-disponiveis")
    public ResponseEntity<List<Sala>> listarSalasDisponiveis() {
        try {
            return ResponseEntity.status(200).body(salaService.listarSalasDisponiveis());
        } catch (CadastroSalaExecption e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/listar-indisponiveis")
    public ResponseEntity<List<Sala>> listarSalasIndisponiveis() {
        try {
            return ResponseEntity.status(200).body(salaService.listarSalasIndisponiveis());
        } catch (CadastroSalaExecption e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}
