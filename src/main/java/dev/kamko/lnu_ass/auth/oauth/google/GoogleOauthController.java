package dev.kamko.lnu_ass.auth.oauth.google;

import java.io.IOException;
import java.util.Map;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import dev.kamko.lnu_ass.auth.AuthService;
import dev.kamko.lnu_ass.config.Api;
import dev.kamko.lnu_ass.core.domain.user.UserService;
import dev.kamko.lnu_ass.auth.oauth.google.dto.GoogleTokens;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Api.API_PREFIX + "/auth/oauth2")
public class GoogleOauthController {

    private final String baseUrl;
    private final AuthorizationCodeFlow authCodeFlow;
    private final UserService userService;
    private final AuthService authService;

    public GoogleOauthController(@Value("${base-url}") String baseUrl,
                                 AuthorizationCodeFlow authCodeFlow,
                                 UserService userService,
                                 AuthService authService) {
        this.baseUrl = baseUrl;
        this.authCodeFlow = authCodeFlow;
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("/initiate")
    public Object initiate() {
        var url = authCodeFlow
                .newAuthorizationUrl()
                .setRedirectUri(callbackUrl())
                .build();

        return Map.of(
                "redirectTo", url
        );
    }

    @GetMapping("/callback")
    public Object callback(String code) throws IOException {

        var tokenResponse = authCodeFlow.newTokenRequest(code)
                .setRedirectUri(callbackUrl())
                .execute();

        var accessToken = tokenResponse.getAccessToken();
        var refreshToken = tokenResponse.getRefreshToken();

        return userService.handleUserAuthentication(GoogleTokens.of(accessToken, refreshToken))
                .thenApply(ewiv -> Map.of(
                        "jwtToken", authService.generateToken(ewiv.getAggregate().getEmail()),
                        "entity", ewiv
                ));
    }

    private String callbackUrl() {
        return baseUrl + Api.API_PREFIX + "/auth/oauth2/callback";
    }

}
