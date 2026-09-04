package com.monitoring.audit_log_crud.security;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.monitoring.audit_log_crud.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        Algorithm alg = Algorithm.HMAC256(secret);
        return JWT.create().withIssuer("audit-api")
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(3600))
                .sign(alg);
    }
    public String validateToken(String token) {
        Algorithm alg = Algorithm.HMAC256(secret);
        return JWT.require(alg).withIssuer("audit-api").build().verify(token).getSubject();
    }
}
