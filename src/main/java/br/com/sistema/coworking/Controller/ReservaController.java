package br.com.sistema.coworking.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.sistema.coworking.DTO.Reserva.AtualizarReservaDTO;
import br.com.sistema.coworking.Entity.Reserva;
import br.com.sistema.coworking.Exception.Records.Reserva.ReservaCadastroException;
import br.com.sistema.coworking.Service.ReservaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/criar")
    public ResponseEntity<String> criarReserva(@RequestBody @Valid Reserva reserva) {
        try {
            reservaService.criarReserva(reserva);
            return ResponseEntity.status(200).body("Reserva criada com sucesso!");
        } catch (ReservaCadastroException e) {
            return ResponseEntity.status(400).body("Erro ao criar reserva: " + e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarReserva(@RequestBody @Valid AtualizarReservaDTO reserva) {
        try {
            reservaService.atualizarReserva(reserva);
            return ResponseEntity.status(200).body("Reserva atualizada com sucesso!");
        } catch (ReservaCadastroException e) {
            return ResponseEntity.status(400).body("Erro ao atualizar reserva: " + e.getMessage());
        }
    }

    @PutMapping("/cancelar")
    public ResponseEntity<String> cancelarReserva(@RequestBody @Valid AtualizarReservaDTO reserva) {
        try {
            reservaService.cancelarReserva(reserva);
            return ResponseEntity.status(200).body("Reserva cancelada com sucesso!");
        } catch (ReservaCadastroException e) {
            return ResponseEntity.status(400).body("Erro ao cancelar reserva: " + e.getMessage());
        }
    }

    @GetMapping("/listar-todas")
    public ResponseEntity<Page<Reserva>> listarReservas(Pageable pageable) {
        try {
            return ResponseEntity.status(200).body(reservaService.listarReservas(pageable));
        } catch (ReservaCadastroException e) {
            return ResponseEntity.status(400).body(Page.empty());
        }
    }

    @GetMapping("/listar-ativas")
    public ResponseEntity<Page<Reserva>> listarReservasAtivas(Pageable pageable) {
        try {
            return ResponseEntity.status(200).body(reservaService.listarReservasAtivas(pageable));
        } catch (ReservaCadastroException e) {
            return ResponseEntity.status(400).body(Page.empty());
        }
    }

    @PostMapping("/listar-por-usuario")
    public ResponseEntity<List<Reserva>> listarReservasPorUsuario(@RequestBody @Valid AtualizarReservaDTO reserva) {
        try {
            System.out.println(">>> Entrou no método listarReservasPorUsuario()");
            return ResponseEntity.status(200).body(reservaService.listarReservasPorUsuario(reserva));
        } catch (ReservaCadastroException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @PostMapping("/obter-reserva")
    public ResponseEntity<String> obterReserva(@RequestBody @Valid AtualizarReservaDTO reserva) {
        try {
            return ResponseEntity.status(200).body(reservaService.obterReserva(reserva).toString());
        } catch (ReservaCadastroException e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}
