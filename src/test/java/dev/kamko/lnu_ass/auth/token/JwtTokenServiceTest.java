package dev.kamko.lnu_ass.auth.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class JwtTokenServiceTest {

    private String secret = "ABCDEFG";

    private JwtTokenService sut;

    @BeforeEach
    private void setUp() {
        sut = new JwtTokenService(secret);
    }

    @Test
    void generateToken() {
        var subject = "kmk@kmk.com";

        var token = sut.generateToken(subject);

        var decoded = JWT.decode(token);
        assertThat(decoded.getSubject()).isEqualTo(subject);
    }

    @Test
    void validTokenIsValid() {
        var token = sut.generateToken("abc");

        boolean result = sut.isValidToken(token);

        assertThat(result).isTrue();
    }

    @Test
    void invalidTokenIsNotValid() {
        var token = JWT.create()
                .withIssuer(JwtTokenService.ISSUER)
                .sign(Algorithm.HMAC256(secret + "DIFFERENT"));

        boolean result = sut.isValidToken(token);

        assertThat(result).isFalse();
    }
}