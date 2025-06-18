package br.com.sistema.coworking.Enum;

public enum TipoReserva {
    SALA("Sala de Reuniao"),
    ESTACAO_DE_TRABALHO("Estacao de Trabalho");

    private String descricao;

    TipoReserva(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

