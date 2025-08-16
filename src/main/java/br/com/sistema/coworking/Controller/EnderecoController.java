package br.com.sistema.coworking.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sistema.coworking.DTO.Endereco.CepRequest;
import br.com.sistema.coworking.DTO.Endereco.EnderecoDTO;
import br.com.sistema.coworking.Service.ConsultaCepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private ConsultaCepService consultaCepService;

    @PostMapping("/consultarCep")
    @Operation(summary = "Consulta CEP", description = "Consulta CEP")
    @Parameters({
            @Parameter(name = "cep", description = "CEP")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CEP consultado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao consultar CEP."),
    })
    public ResponseEntity<Map<String, EnderecoDTO>> consultarCep(@RequestBody CepRequest request) {
        try {
            EnderecoDTO endereco = consultaCepService.consultarCep(request.getCep());
            Map<String, EnderecoDTO> resposta = new HashMap<>();
            resposta.put("endereco", endereco);
            return ResponseEntity.ok(resposta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
