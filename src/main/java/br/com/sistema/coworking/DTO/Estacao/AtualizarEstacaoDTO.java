package br.com.sistema.coworking.DTO.Estacao;

import br.com.sistema.coworking.Entity.Sala;

public record AtualizarEstacaoDTO(
        Long id,
        String identificador,
        String descricao,
        Boolean disponivel,
        Boolean monitor,
        Boolean tecladoMouse,
        Boolean cadeiraErgonomica,
        String fotoUrl,
        Sala sala) {

}
