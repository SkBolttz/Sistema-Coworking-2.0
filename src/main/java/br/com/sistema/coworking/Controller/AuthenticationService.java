package br.com.sistema.coworking.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import br.com.sistema.coworking.DTO.Login.LoginDTO;
import br.com.sistema.coworking.Entity.Visitante;
import br.com.sistema.coworking.Exception.Records.Cadastro.CadastroException;
import br.com.sistema.coworking.Security.TokenJWT;
import br.com.sistema.coworking.Security.TokenService;
import br.com.sistema.coworking.Service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Controller responsável por gerenciar as informações de autenticação.")
public class AuthenticationService {

        @Autowired
        private AuthenticationManager authManager;

        @Autowired
        private TokenService tokenService;

        @Autowired
        private LoginService loginService;

        @PostMapping("/login")
        @Operation(summary = "Login", description = "Efetua o login do usuário.")
        @Parameters({
                        @Parameter(name = "cpf", description = "CPF do usuário."),
                        @Parameter(name = "senha", description = "Senha do usuário.")
        })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Login efetuado com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Erro ao efetuar login.")
        })
        public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                loginDTO.cpf(), loginDTO.senha());

                Authentication authentication = authManager.authenticate(authenticationToken);

                Visitante usuario = (Visitante) authentication.getPrincipal();
                String token = tokenService.gerarToken(usuario);

                return ResponseEntity.ok(new TokenJWT(token));
        }

        @PostMapping(value = "/registro", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        @Operation(summary = "Cadastro", description = "Efetua o cadastro do usuário.")
        @Parameters({
                        @Parameter(name = "visitante", description = "Informações do usuário para cadastro."),
                        @Parameter(name = "file", description = "Foto do documento de identificação.")
        })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Erro ao realizar cadastro.")
        })
        public ResponseEntity<?> registrar(
                        @RequestPart("visitante") Visitante visitante,
                        @RequestPart("file") MultipartFile file) {
                try {
                        loginService.registro(visitante, file);
                        return ResponseEntity.status(200).body("Cadastro realizado com sucesso!");
                } catch (CadastroException e) {
                        return ResponseEntity.status(400).body("Falha ao realizar cadastro: " + e.getMessage());
                }
        }
}
