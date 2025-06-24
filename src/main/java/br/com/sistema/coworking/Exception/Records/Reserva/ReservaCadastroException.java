package br.com.sistema.coworking.Exception.Records.Reserva;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservaCadastroException extends RuntimeException {

    private String erro;
    private String detalhe;

    public ReservaCadastroException(String erro, String detalhe) {
        this.erro = erro;
        this.detalhe = detalhe;
    }
}
