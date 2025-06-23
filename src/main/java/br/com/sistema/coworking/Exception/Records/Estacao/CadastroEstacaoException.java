package br.com.sistema.coworking.Exception.Records.Estacao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroEstacaoException extends RuntimeException {

    private String erro;
    private String detalhe;

    public CadastroEstacaoException(String erro, String detalhe) {
        this.erro = erro;
        this.detalhe = detalhe;
    }
}
