package br.com.sistema.coworking.Enum;

public enum AprovacaoStatus {
    APROVADA("Aprovada"),
    PENDENTE("Pendente"),
    REPROVADA("Reprovada");

    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    AprovacaoStatus(String descricao) {
        this.descricao = descricao;
    }
}
