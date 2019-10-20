package dev.kamko.lnu_ass.auth;

import java.util.Map;

import dev.kamko.lnu_ass.config.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Api.AUTH_PREFIX)
public class AuthController {

    private final JwtTokenService jwtTokenService;

    public AuthController(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @GetMapping("/token/{token}/verification")
    public Map<String, Object> verifyToken(@PathVariable("token") String token) {
        return Map.of(
                "token", token,
                "valid", jwtTokenService.isValidToken(token)
        );
    }

}
