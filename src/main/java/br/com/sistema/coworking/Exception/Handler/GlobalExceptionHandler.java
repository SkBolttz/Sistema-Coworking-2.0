package br.com.sistema.coworking.Exception.Handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.sistema.coworking.Exception.Records.Cadastro.CadastroException;
import br.com.sistema.coworking.Exception.Records.Cadastro.CpfExecption;
import br.com.sistema.coworking.Exception.Records.Cadastro.EmailException;
import br.com.sistema.coworking.Exception.Records.Cadastro.EmpresaCadastroException;
import br.com.sistema.coworking.Exception.Records.Empresa.AtualizarEmpresaException;
import br.com.sistema.coworking.Exception.Records.Empresa.CadastroEmpresaException;
import br.com.sistema.coworking.Exception.Records.Estacao.AtualizarEstacaoException;
import br.com.sistema.coworking.Exception.Records.Estacao.CadastroEstacaoException;
import br.com.sistema.coworking.Exception.Records.Estacao.EstacaoException;
import br.com.sistema.coworking.Exception.Records.Estacao.EstacaoReservaException;
import br.com.sistema.coworking.Exception.Records.Ramo.CadastroRamoException;
import br.com.sistema.coworking.Exception.Records.Reserva.ReservaCadastroException;
import br.com.sistema.coworking.Exception.Records.Reserva.ReservaException;
import br.com.sistema.coworking.Exception.Records.Sala.CadastroSalaExecption;
import br.com.sistema.coworking.Exception.Records.Sala.ReservaSalaException;
import br.com.sistema.coworking.Exception.Records.Sala.SalaException;
import br.com.sistema.coworking.Exception.Records.Visitante.DadosException;
import br.com.sistema.coworking.Exception.Records.Visitante.VisitanteException ;

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

    @ExceptionHandler(CadastroEmpresaException.class)
    public ResponseEntity<ErrorResponse> handlerCadastroEmpresaException(CadastroEmpresaException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErro(), ex.getDetalhe());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(CadastroEstacaoException.class)
    public ResponseEntity<ErrorResponse> handlerCadastroEstacaoException(CadastroEstacaoException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErro(), ex.getDetalhe());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(AtualizarEstacaoException.class)
    public ResponseEntity<ErrorResponse> handlerAtualizarEstacaoException(AtualizarEstacaoException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErro(), ex.getDetalhe());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ReservaCadastroException.class)
    public ResponseEntity<ErrorResponse> handlerReservaException(ReservaCadastroException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErro(), ex.getDetalhe());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(EstacaoReservaException.class)
    public ResponseEntity<ErrorResponse> handlerEstacaoReservaException(EstacaoReservaException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErro(), ex.getDetalhe());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ReservaSalaException.class)
    public ResponseEntity<ErrorResponse> handlerReservaSalaException(ReservaSalaException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErro(), ex.getDetalhe());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(VisitanteException .class)
    public ResponseEntity<ErrorResponse> handlerVisitanteException(VisitanteException  ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErro(), ex.getDetalhe());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ReservaException.class)
    public ResponseEntity<ErrorResponse> handlerReservaException(ReservaException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErro(), ex.getDetalhe());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(AtualizarEmpresaException.class)
    public ResponseEntity<ErrorResponse> handlerAtualizarEmpresaException(AtualizarEmpresaException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErro(), ex.getDetalhe());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(EstacaoException.class)
    public ResponseEntity<ErrorResponse> handlerEstacaoException(EstacaoException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErro(), ex.getDetalhe());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(SalaException.class)
    public ResponseEntity<ErrorResponse> handlerSalaException(SalaException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErro(), ex.getDetalhe());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(CadastroRamoException.class)
    public ResponseEntity<ErrorResponse> handlerCadastroRamoException(CadastroRamoException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErro(), ex.getDetalhe());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
