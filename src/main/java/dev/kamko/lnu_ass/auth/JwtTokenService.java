package dev.kamko.lnu_ass.auth;

import java.sql.Date;
import java.time.Instant;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

    static final String ISSUER = "lnu.kamko.dev";

    private final Algorithm algorithm;

    public JwtTokenService(@Value("${auth.jwt.secret}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String generateToken(String subject) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(subject)
                .withNotBefore(Date.from(Instant.now()))
                .withExpiresAt(Date.from(Instant.now().plusSeconds(3600)))
                .sign(algorithm);
    }

    boolean isValidToken(String token) {
        try {
            JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
