package br.com.sistema.coworking.Exception.Records.Cadastro;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CpfExecption extends RuntimeException{
    
    private final String erro;
    private final String detalhe;

    public CpfExecption(String erro, String detalhe){
        this.detalhe = detalhe;
        this.erro = erro;
    }
}
