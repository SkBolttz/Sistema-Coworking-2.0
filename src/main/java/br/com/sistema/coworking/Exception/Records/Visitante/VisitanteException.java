package br.com.sistema.coworking.Exception.Records.Visitante;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitanteException extends RuntimeException {

    private String erro;
    private String detalhe;

    public VisitanteException (String erro, String detalhe) {
        this.erro = erro;
        this.detalhe = detalhe;
    }
}
