package br.com.sistema.coworking.DTO.Visitante;

public record AtualizarVisitante(
        String cpf,
        String nomeCompleto,
        String telefone,
        String senha,
        String observacao,
        String fotoDocumento,
        String email) {

    public AtualizarVisitante(String cpf, String nomeCompleto, String telefone, String senha, String observacao,
            String fotoDocumento, String email) {
        this.cpf = cpf;
        this.nomeCompleto = nomeCompleto;
        this.telefone = telefone;
        this.senha = senha;
        this.observacao = observacao;
        this.fotoDocumento = fotoDocumento;
        this.email = email;
    }

}
