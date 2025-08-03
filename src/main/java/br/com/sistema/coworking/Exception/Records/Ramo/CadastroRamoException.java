package br.com.sistema.coworking.Exception.Records.Ramo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroRamoException extends RuntimeException {

    private String erro;
    private String detalhe;

    public CadastroRamoException(String erro, String detalhe) {
        this.erro = erro;
        this.detalhe = detalhe;
    }
}
