package br.com.sistema.coworking.Enum;

public enum StatusReserva {
    ATIVA("Ativa"),
    FINALIZADA("Finalizada"),
    CANCELADA("Cancelada"),
    EXPIRADA("Expirada");

    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    StatusReserva(String descricao) {
        this.descricao = descricao;
    }
}
