package br.com.sistema.coworking.Exception.Records.Sala;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroSalaExecption extends RuntimeException{
    
    private final String erro;
    private final String detalhe;

    public CadastroSalaExecption(String erro, String detalhe){
        this.erro = erro;
        this.detalhe = detalhe;
    }
}
