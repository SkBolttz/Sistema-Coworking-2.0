package br.com.sistema.coworking.DTO.Visitante;

public record AtualizarVisitante(
    String cpf,
    String nomeCompleto,
    String telefone,
    String senha,
    String observacao,
    String fotoDocumento,
    String email
) {
    
}
