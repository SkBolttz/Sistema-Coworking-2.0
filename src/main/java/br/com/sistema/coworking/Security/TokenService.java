package br.com.sistema.coworking.Security;

import java.sql.Date;
import org.springframework.stereotype.Service;
import br.com.sistema.coworking.Entity.Visitante;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

    private String secret = "segredo123456";
    private long expiration = 86400000;

    public String gerarToken(Visitante usuario) {
        return Jwts.builder()
                .setIssuer("API Coworking")
                .setSubject(usuario.getEmail())
                .setIssuedAt(new Date(expiration))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getSubject(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
