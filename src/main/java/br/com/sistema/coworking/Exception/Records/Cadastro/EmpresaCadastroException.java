package br.com.sistema.coworking.Exception.Records.Cadastro;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaCadastroException extends RuntimeException{
    
    private final String erro;
    private final String detalhe;

    public EmpresaCadastroException(String erro, String detalhe){
        this.detalhe = detalhe;
        this.erro = erro;
    }
}
