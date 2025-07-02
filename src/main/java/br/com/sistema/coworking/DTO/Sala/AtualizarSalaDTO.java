package br.com.sistema.coworking.DTO.Sala;

import br.com.sistema.coworking.Enum.TipoSala;

public record AtualizarSalaDTO(
        long id,
        String nome,
        String descricao,
        int quantidade,
        boolean disponivel,
        TipoSala tipo,
        String localizacao,
        String fotoUrl) {

    public AtualizarSalaDTO(long id, String nome, String descricao, int quantidade, boolean disponivel,
            String localizacao, String fotoUrl) {
        this(id, nome, descricao, quantidade, disponivel, null, localizacao, fotoUrl);
    }
}
