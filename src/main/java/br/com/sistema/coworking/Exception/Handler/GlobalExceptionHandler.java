package br.com.sistema.coworking.Exception.Handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.sistema.coworking.Exception.Records.Cadastro.CadastroException;
import br.com.sistema.coworking.Exception.Records.Cadastro.CpfExecption;
import br.com.sistema.coworking.Exception.Records.Cadastro.EmailException;
import br.com.sistema.coworking.Exception.Records.Cadastro.EmpresaCadastroException;
import br.com.sistema.coworking.Exception.Records.Sala.CadastroSalaExecption;
import br.com.sistema.coworking.Exception.Records.Visitante.DadosException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CadastroException.class)
    public ResponseEntity<ErrorResponse> handleCadastroException(CadastroException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErro(), ex.getDetalhe());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<ErrorResponse> handlerEmailException(EmailException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErro(), ex.getDetalhe());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(CpfExecption.class)
    public ResponseEntity<ErrorResponse> handlerCpfException(CpfExecption ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErro(), ex.getDetalhe());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(EmpresaCadastroException.class)
    public ResponseEntity<ErrorResponse> handlerEmpresaException(EmpresaCadastroException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErro(), ex.getDetalhe());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DadosException.class)
    public ResponseEntity<ErrorResponse> handlerDadosException(DadosException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErro(), ex.getDetalhe());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(CadastroSalaExecption.class)
    public ResponseEntity<ErrorResponse> handlerCadastroSalaException(CadastroSalaExecption ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErro(), ex.getDetalhe());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
