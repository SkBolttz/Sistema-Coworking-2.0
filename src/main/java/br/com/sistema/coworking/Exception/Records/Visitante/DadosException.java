package br.com.sistema.coworking.Exception.Records.Visitante;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DadosException extends RuntimeException{
    
    private final String erro;
    private final String detalhe;

    public DadosException(String erro, String detalhe){
        this.erro = erro;
        this.detalhe = detalhe;
    }
}
