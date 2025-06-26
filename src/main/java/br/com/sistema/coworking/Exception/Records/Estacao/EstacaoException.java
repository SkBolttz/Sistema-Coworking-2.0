package br.com.sistema.coworking.Exception.Records.Estacao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstacaoException extends RuntimeException {

    private String erro;
    private String detalhe;

    public EstacaoException(String erro, String detalhe) {
        this.erro = erro;
        this.detalhe = detalhe;
    }
}
