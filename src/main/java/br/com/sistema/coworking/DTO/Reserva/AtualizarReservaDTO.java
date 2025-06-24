package br.com.sistema.coworking.DTO.Reserva;

import br.com.sistema.coworking.Enum.TipoReserva;

public record AtualizarReservaDTO(
    TipoReserva tipo,
    String observacao
) {
    
}
