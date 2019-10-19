package dev.kamko.lnu_ass.oauth.google;

import java.io.IOException;
import java.util.Map;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import dev.kamko.lnu_ass.config.Api;
import dev.kamko.lnu_ass.core.domain.user.UserService;
import dev.kamko.lnu_ass.oauth.google.dto.GoogleTokens;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(value = Api.API_PREFIX + "/oauth2")
public class GoogleOauthController {

    private final String baseUrl;
    private final AuthorizationCodeFlow authCodeFlow;
    private final UserService userService;

    public GoogleOauthController(@Value("${base-url}") String baseUrl,
                                 AuthorizationCodeFlow authCodeFlow,
                                 UserService userService) {
        this.baseUrl = baseUrl;
        this.authCodeFlow = authCodeFlow;
        this.userService = userService;
    }

    @GetMapping("/initiate")
    public RedirectView initiate() {
        var url = authCodeFlow
                .newAuthorizationUrl()
                .setRedirectUri(callbackUrl())
                .build();

        return new RedirectView(url);
    }

    @GetMapping("/callback")
    public Map callback(String code) throws IOException {

        var tokenResponse = authCodeFlow.newTokenRequest(code)
                .setRedirectUri(callbackUrl())
                .execute();

        var accessToken = tokenResponse.getAccessToken();
        var refreshToken = tokenResponse.getRefreshToken();

        userService.registerUser(GoogleTokens.of(accessToken, refreshToken));

        return Map.of("accessToken", accessToken);
    }

    @PostMapping("/verify-token")
    public void verify() {
    }

    private String callbackUrl() {
        return baseUrl + Api.API_PREFIX + "/oauth2/callback";
    }

}
