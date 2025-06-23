package br.com.sistema.coworking.DTO.Empresa;

import br.com.sistema.coworking.Entity.Endereco;
import br.com.sistema.coworking.Entity.Visitante;

public record AtualizarEmpresaDTO(
    String nomeFantasia,
    String razaoSocial,
    String email,
    String telefone,
    String ramo,
    Visitante responsavel,
    Endereco endereco,
    String cnpj

) {
    
}
