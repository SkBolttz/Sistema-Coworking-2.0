package br.com.sistema.coworking.Exception.Records.Estacao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstacaoReservaException extends RuntimeException {

    private String erro;
    private String detalhe;

    public EstacaoReservaException(String erro, String detalhe) {
        this.erro = erro;
        this.detalhe = detalhe;
    }
}
