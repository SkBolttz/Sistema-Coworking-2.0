package br.com.sistema.coworking.DTO.Empresa;

import br.com.sistema.coworking.Entity.Endereco;
import br.com.sistema.coworking.Entity.Visitante;
import br.com.sistema.coworking.Entity.RamoEmpresarial;

public record AtualizarEmpresaDTO(
        String nomeFantasia,
        String razaoSocial,
        String email,
        String telefone,
        RamoEmpresarial ramo,
        Visitante responsavel,
        Endereco endereco,
        String cnpj) {

    public AtualizarEmpresaDTO(String cnpj, String nomeFantasia) {
        this(nomeFantasia, null, null, null, null, null, null, cnpj);
    }

    public AtualizarEmpresaDTO(String cnpj, String nomeFantasia, Visitante responsavel) {
        this(nomeFantasia, null, null, null, null, responsavel, null, cnpj);
    }
}
