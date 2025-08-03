package br.com.sistema.coworking.DTO.Visitante;

public record AtualizarVisitante(
        long id,
        String cpf,
        String nomeCompleto,
        String telefone,
        String senha,
        String observacao,
        String fotoDocumento,
        String email,
        String novaSenha,
        String confirmaSenha) {

    public AtualizarVisitante(long id, String cpf, String nomeCompleto, String telefone, String senha,
            String observacao,
            String fotoDocumento, String email, String novaSenha, String confirmaSenha) {
        this.id = id;
        this.cpf = cpf;
        this.nomeCompleto = nomeCompleto;
        this.telefone = telefone;
        this.senha = senha;
        this.observacao = observacao;
        this.fotoDocumento = fotoDocumento;
        this.email = email;
        this.novaSenha = novaSenha;
        this.confirmaSenha = confirmaSenha;
    }

}
