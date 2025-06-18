package br.com.sistema.coworking.Enum;

public enum TipoVisitante {
    VISITANTE("Visitante"),
    FUNCIONARIO("Funcionario"),
    EMPRESA("Empresa"),
    ADMIN("Admin");

    private String descricao;

    TipoVisitante(String descricao) {
        this.descricao = descricao;
    }   

    public String getDescricao() {
        return descricao;
    }
}
