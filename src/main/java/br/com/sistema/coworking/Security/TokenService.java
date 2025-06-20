package br.com.sistema.coworking.Security;

import java.sql.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import br.com.sistema.coworking.Entity.Visitante;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.expiration}")
    private long expirationMillis;

    public String gerarToken(Visitante usuario) {
        return JWT.create()
                .withIssuer("API Coworking")
                .withSubject(usuario.getCpf())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationMillis))
                .sign(Algorithm.HMAC256(secret));
    }

    public String validarToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("API Coworking")
                .build()
                .verify(token)
                .getSubject();
    }
}
