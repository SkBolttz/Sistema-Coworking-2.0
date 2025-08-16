package br.com.sistema.coworking.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.sistema.coworking.DTO.Empresa.AtualizarEmpresaDTO;
import br.com.sistema.coworking.Entity.Empresa;
import br.com.sistema.coworking.Exception.Records.Empresa.CadastroEmpresaException;
import br.com.sistema.coworking.Service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/empresa")
@Tag(name = "Empresa", description = "Controller responsável por gerenciar as informações das empresas.")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar empresa", description = "Cria uma nova empresa com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "empresa", description = "Informações da empresa para criar.")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa cadastrada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar empresa.")
    })
    public ResponseEntity<String> cadastroEmpresa(@RequestBody @Valid Empresa empresa) {
        try {
            empresaService.cadastro(empresa);
            return ResponseEntity.status(200).body("Empresa cadastrada com sucesso!");
        } catch (CadastroEmpresaException e) {
            return ResponseEntity.status(400).body("Erro ao cadastrar empresa: " + e.getMessage());
        }
    }

    @PutMapping("/ativar")
    @Operation(summary = "Ativar empresa", description = "Ativa uma empresa com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "empresa", description = "Informações da empresa para ativar.")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa ativada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao ativar empresa.")
    })
    public ResponseEntity<String> ativarEmpresa(@RequestBody @Valid AtualizarEmpresaDTO empresa) {
        try {
            empresaService.ativar(empresa);
            return ResponseEntity.status(200).body("Empresa ativada com sucesso!");
        } catch (CadastroEmpresaException e) {
            return ResponseEntity.status(400).body("Erro ao ativar empresa: " + e.getMessage());
        }
    }

    @PutMapping("/desativar")
    @Operation(summary = "Desativar empresa", description = "Desativa uma empresa com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "empresa", description = "Informações da empresa para desativar.")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa desativada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao desativar empresa.")
    })
    public ResponseEntity<String> desativarEmpresa(@RequestBody @Valid AtualizarEmpresaDTO empresa) {
        try {
            empresaService.desativar(empresa);
            return ResponseEntity.status(200).body("Empresa desativada com sucesso!");
        } catch (CadastroEmpresaException e) {
            return ResponseEntity.status(400).body("Erro ao desativar empresa: " + e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    @Operation(summary = "Atualizar empresa", description = "Atualiza uma empresa existente com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "empresa", description = "Informações da empresa para atualizar.")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar empresa.")
    })
    public ResponseEntity<String> atualizarEmpresa(@RequestBody @Valid AtualizarEmpresaDTO empresa) {
        try {
            empresaService.atualizar(empresa);
            return ResponseEntity.status(200).body("Empresa atualizada com sucesso!");
        } catch (CadastroEmpresaException e) {
            return ResponseEntity.status(400).body("Erro ao atualizar empresa: " + e.getMessage());
        }
    }

    @PostMapping("/obter")
    @Operation(summary = "Obter empresa", description = "Obtem uma empresa com base nas informações fornecidas no corpo da requisição.")
    @Parameters({
            @Parameter(name = "empresa", description = "Informações da empresa para obter.")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa obtida com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao obter empresa.")
    })
    public ResponseEntity<Empresa> obterEmpresa(@RequestBody @Valid AtualizarEmpresaDTO empresa) {
        try {
            Empresa empresaObtida = empresaService.obter(empresa);
            return ResponseEntity.ok(empresaObtida);
        } catch (CadastroEmpresaException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/listar-todas")
    @Operation(summary = "Listar todas as empresas", description = "Lista todas as empresas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresas obtidas com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao obter empresas."),
    })
    public ResponseEntity<Page<Empresa>> listarEmpresas(
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            return ResponseEntity.status(200).body(empresaService.listarEmpresas(pageable));
        } catch (CadastroEmpresaException e) {
            return ResponseEntity.status(400).body(Page.empty());
        }
    }

    @GetMapping("/listar-ativas")
    @Operation(summary = "Listar todas as empresas ativas", description = "Lista todas as empresas ativas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresas ativas obtidas com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao obter empresas ativas."),
    })
    public ResponseEntity<Page<Empresa>> listarEmpresasAtivas(
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            return ResponseEntity.status(200).body(empresaService.listarEmpresasAtivas(pageable));
        } catch (CadastroEmpresaException e) {
            return ResponseEntity.status(400).body(Page.empty());
        }
    }

    @GetMapping("/listar-inativas")
    @Operation(summary = "Listar todas as empresas inativas", description = "Lista todas as empresas inativas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresas inativas obtidas com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao obter empresas inativas."),
    })
    public ResponseEntity<Page<Empresa>> listarEmpresasInativas(
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            return ResponseEntity.status(200).body(empresaService.listarEmpresasInativas(pageable));
        } catch (CadastroEmpresaException e) {
            return ResponseEntity.status(400).body(Page.empty());
        }
    }
}
 