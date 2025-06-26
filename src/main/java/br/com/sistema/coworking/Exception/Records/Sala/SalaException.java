package br.com.sistema.coworking.Exception.Records.Sala;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaException extends RuntimeException {

    private String erro;
    private String detalhe;

    public SalaException(String erro, String detalhe) {
        this.erro = erro;
        this.detalhe = detalhe;
    }
}
