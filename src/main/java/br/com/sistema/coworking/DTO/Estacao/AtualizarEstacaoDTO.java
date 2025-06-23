package br.com.sistema.coworking.DTO.Estacao;

public record AtualizarEstacaoDTO(
                long id,
                String identificador,
                String descricao,
                Boolean disponivel,
                Boolean monitor,
                Boolean tecladoMouse,
                Boolean cadeiraErgonomica,
                String fotoUrl) {

}
