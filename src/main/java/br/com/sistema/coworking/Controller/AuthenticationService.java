package br.com.sistema.coworking.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sistema.coworking.DTO.Login.LoginDTO;
import br.com.sistema.coworking.Entity.Visitante;
import br.com.sistema.coworking.Exception.Records.Cadastro.CadastroException;
import br.com.sistema.coworking.Security.TokenJWT;
import br.com.sistema.coworking.Security.TokenService;
import br.com.sistema.coworking.Service.LoginService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.cpf(), loginDTO.senha());

        Authentication authentication = authManager.authenticate(authenticationToken);

        Visitante usuario = (Visitante) authentication.getPrincipal();
        String token = tokenService.gerarToken(usuario);

        return ResponseEntity.ok(new TokenJWT(token));
    }

    @PostMapping("/registro")
    public ResponseEntity<String> registrar(@RequestBody Visitante visitante) {
        try{
            loginService.registro(visitante);
            return ResponseEntity.status(200).body("Cadastro realizado com sucesso!");
        }catch(CadastroException e){
            return ResponseEntity.status(400).body("Falha ao realizar cadastro: " + e.getMessage());
        }
    }
}
