package br.com.sistema.coworking.Controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import br.com.sistema.coworking.DTO.Reserva.AtualizarReservaDTO;
import br.com.sistema.coworking.Entity.Reserva;
import br.com.sistema.coworking.Exception.Records.Reserva.ReservaCadastroException;
import br.com.sistema.coworking.Service.ReservaService;

@RestController
@RequestMapping("/reserva")
@Tag(name = "Reserva", description = "API para gerenciamento de reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar reserva", description = "Cria uma nova reserva com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "reserva", description = "Informações da reserva para criar."),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao criar reserva."),
    })
    public ResponseEntity<String> criarReserva(@RequestBody @Valid Reserva reserva) {
        try {
            reservaService.criarReserva(reserva);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reserva criada com sucesso!");
        } catch (ReservaCadastroException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar reserva: " + e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    @Operation(summary = "Atualizar reserva", description = "Atualiza uma reserva existente com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "reserva", description = "Informações da reserva para atualizar."),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar reserva."),
    })
    public ResponseEntity<String> atualizarReserva(@RequestBody @Valid AtualizarReservaDTO reserva) {
        try {
            reservaService.atualizarReserva(reserva);
            return ResponseEntity.status(HttpStatus.OK).body("Reserva atualizada com sucesso!");
        } catch (ReservaCadastroException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar reserva: " + e.getMessage());
        }
    }

    @PutMapping("/cancelar")
    @Operation(summary = "Cancelar reserva", description = "Cancela uma reserva existente com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "reserva", description = "Informações da reserva para cancelar."),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva cancelada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao cancelar reserva."),
    })
    public ResponseEntity<String> cancelarReserva(@RequestBody @Valid AtualizarReservaDTO reserva) {
        try {
            reservaService.cancelarReserva(reserva);
            return ResponseEntity.status(HttpStatus.OK).body("Reserva cancelada com sucesso!");
        } catch (ReservaCadastroException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cancelar reserva: " + e.getMessage());
        }
    }

    @GetMapping("/listar-todas")
    @Operation(summary = "Listar todas as reservas", description = "Lista todas as reservas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reservas."),
            @ApiResponse(responseCode = "400", description = "Erro ao listar reservas."),
    })
    public ResponseEntity<Page<Reserva>> listarReservas(Pageable pageable) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(reservaService.listarReservas(pageable));
        } catch (ReservaCadastroException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Page.empty());
        }
    }

    @GetMapping("/listar-ativas")
    @Operation(summary = "Listar reservas ativas", description = "Lista todas as reservas ativas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reservas ativas."),
            @ApiResponse(responseCode = "400", description = "Erro ao listar reservas ativas."),
    })
    public ResponseEntity<Page<Reserva>> listarReservasAtivas(Pageable pageable) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(reservaService.listarReservasAtivas(pageable));
        } catch (ReservaCadastroException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Page.empty());
        }
    }

    @PostMapping("/listar-por-usuario")
    @Operation(summary = "Listar reservas por usuário", description = "Lista todas as reservas de um usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reservas do usuário."),
            @ApiResponse(responseCode = "400", description = "Erro ao listar reservas do usuário."),
    })
    public ResponseEntity<List<Reserva>> listarReservasPorUsuario(@RequestBody @Valid AtualizarReservaDTO reserva) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(reservaService.listarReservasPorUsuario(reserva));
        } catch (ReservaCadastroException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/obter-reserva")
    @Operation(summary = "Obter reserva", description = "Obtém uma reserva existente com base nas informações fornecidas no corpo da requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva obtida com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao obter reserva."),
    })
    public ResponseEntity<String> obterReserva(@RequestBody @Valid AtualizarReservaDTO reserva) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(reservaService.obterReserva(reserva).toString());
        } catch (ReservaCadastroException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
