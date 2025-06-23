package br.com.sistema.coworking.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sistema.coworking.DTO.Empresa.AtualizarEmpresaDTO;
import br.com.sistema.coworking.Entity.Empresa;
import br.com.sistema.coworking.Exception.Records.Empresa.CadastroEmpresaException;
import br.com.sistema.coworking.Service.EmpresaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastroEmpresa(@RequestBody @Valid Empresa empresa) {
        try {
            empresaService.cadastro(empresa);
            return ResponseEntity.status(200).body("Empresa cadastrada com sucesso!");
        } catch (CadastroEmpresaException e) {
            return ResponseEntity.status(400).body("Erro ao cadastrar empresa: " + e.getMessage());
        }
    }

    @PutMapping("/ativar")
    public ResponseEntity<String> ativarEmpresa(@RequestBody @Valid AtualizarEmpresaDTO empresa) {
        try {
            empresaService.ativar(empresa);
            return ResponseEntity.status(200).body("Empresa ativada com sucesso!");
        } catch (CadastroEmpresaException e) {
            return ResponseEntity.status(400).body("Erro ao ativar empresa: " + e.getMessage());
        }
    }

    @PutMapping("/desativar")
    public ResponseEntity<String> desativarEmpresa(@RequestBody @Valid AtualizarEmpresaDTO empresa) {
        try {
            empresaService.desativar(empresa);
            return ResponseEntity.status(200).body("Empresa desativada com sucesso!");
        } catch (CadastroEmpresaException e) {
            return ResponseEntity.status(400).body("Erro ao desativar empresa: " + e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarEmpresa(@RequestBody @Valid AtualizarEmpresaDTO empresa) {
        try {
            empresaService.atualizar(empresa);
            return ResponseEntity.status(200).body("Empresa atualizada com sucesso!");
        } catch (CadastroEmpresaException e) {
            return ResponseEntity.status(400).body("Erro ao atualizar empresa: " + e.getMessage());
        }
    }

    @PostMapping("/obter")
    public ResponseEntity<String> obterEmpresa(@RequestBody @Valid AtualizarEmpresaDTO empresa) {
        try {
            return ResponseEntity.status(200).body(empresaService.obter(empresa).toString());
        } catch (CadastroEmpresaException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/listar-todas")
    public ResponseEntity<List<Empresa>> listarEmpresas() {
        try {
            return ResponseEntity.status(200).body(empresaService.listarEmpresas());
        } catch (CadastroEmpresaException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/listar-ativas")
    public ResponseEntity<List<Empresa>> listarEmpresasAtivas() {
        try {
            return ResponseEntity.status(200).body(empresaService.listarEmpresasAtivas());
        } catch (CadastroEmpresaException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/listar-inativas")
    public ResponseEntity<List<Empresa>> listarEmpresasInativas() {
        try {
            return ResponseEntity.status(200).body(empresaService.listarEmpresasInativas());
        } catch (CadastroEmpresaException e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}
