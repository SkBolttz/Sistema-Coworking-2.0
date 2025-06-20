package br.com.sistema.coworking.Exception.Records.Sala;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarSalaException extends RuntimeException{
    
    private final String erro;
    private final String detalhe;

    public AtualizarSalaException(String erro, String detalhe){
        this.erro = erro;
        this.detalhe = detalhe;
    }
}
