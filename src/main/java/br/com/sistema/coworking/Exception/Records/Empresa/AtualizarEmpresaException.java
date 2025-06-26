package br.com.sistema.coworking.Exception.Records.Empresa;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarEmpresaException extends RuntimeException {

    private String erro;
    private String detalhe;

    public AtualizarEmpresaException(String erro, String detalhe) {
        this.erro = erro;
        this.detalhe = detalhe;
    }
}
