package br.com.sistema.coworking.Exception.Records.Visitante;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarDadosException extends RuntimeException{
    
    private final String erro;
    private final String detalhe;

    public AtualizarDadosException(String erro, String detalhe) {
        this.erro = erro;
        this.detalhe = detalhe;
    }
}
