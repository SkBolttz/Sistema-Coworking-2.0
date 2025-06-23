package br.com.sistema.coworking.Exception.Records.Empresa;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroEmpresaException extends RuntimeException{
    
    private final String erro;
    private final String detalhe;

    public CadastroEmpresaException(String erro, String detalhe){
        this.erro = erro;
        this.detalhe = detalhe;
    }
}
