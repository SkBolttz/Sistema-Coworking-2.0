package br.com.sistema.coworking.Exception.Records.Sala;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservaSalaException extends RuntimeException {

    private String erro;
    private String detalhe;

    public ReservaSalaException(String erro, String detalhe) {
        this.erro = erro;
        this.detalhe = detalhe;
    }
}
