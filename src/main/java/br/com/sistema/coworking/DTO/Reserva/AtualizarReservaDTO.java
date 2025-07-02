package br.com.sistema.coworking.DTO.Reserva;

import java.time.LocalDateTime;

import br.com.sistema.coworking.Entity.Visitante;
import br.com.sistema.coworking.Enum.TipoReserva;

public record AtualizarReservaDTO(
        long id,
        TipoReserva tipo,
        String observacao,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        Visitante visitante) {

    public AtualizarReservaDTO(long id, TipoReserva tipo, String observacao, LocalDateTime dataInicio,
            LocalDateTime dataFim) {
        this(id, null, observacao, null, null, null);
    }
}
