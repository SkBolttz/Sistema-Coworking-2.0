package br.com.sistema.coworking.Enum;

public enum TipoVisitante {
    VISITANTE("Visitante"), // Qualquer email sem @coworking .. @coworkingadmin
    FUNCIONARIO("Funcionario"), // Deve conter @coworking.com.br
    EMPRESA("Empresa"), // Somente para visitante que cadastraram sua empresa
    ADMIN("Admin"); // Deve conter @coworkingadmin.com.br

    private String descricao;

    TipoVisitante(String descricao) {
        this.descricao = descricao;
    }   

    public String getDescricao() {
        return descricao;
    }
}
