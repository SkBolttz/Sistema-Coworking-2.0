package br.com.sistema.coworking.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.sistema.coworking.Entity.RamoEmpresarial;
import br.com.sistema.coworking.Service.RamoService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/ramo")
@Tag(name = "Ramo", description = "Operações relacionadas ao ramo empresarial")
public class RamoController {

    @Autowired
    private RamoService ramoService;

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar ramo", description = "Cria um novo ramo com base nas informações fornecidas no corpo da requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ramo cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar ramo.")
    })
    @Parameters({
            @Parameter(name = "ramo", description = "Informações do ramo para criar.")
    })
    public ResponseEntity<String> criarRamo(@RequestBody @Valid RamoEmpresarial ramo) {
        try {
            ramoService.criarRamo(ramo);
            return ResponseEntity.status(201).body("Ramo cadastrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao cadastrar ramo: " + e.getMessage());
        }
    }

    @GetMapping("/obter-ramos")     
    @Operation(summary = "Obter Ramos", description = "Realiza a listagem de todos os ramos cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ramos obtidos com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao obter ramos.")
    })
    public ResponseEntity<List<RamoEmpresarial>> obterRamos() {
        try {
            return ResponseEntity.status(200).body(ramoService.obterRamos());
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}
