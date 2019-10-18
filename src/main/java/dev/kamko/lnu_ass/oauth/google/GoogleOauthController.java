package dev.kamko.lnu_ass.oauth.google;

import java.io.IOException;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import dev.kamko.lnu_ass.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(value = Api.API_PREFIX + "/oauth2")
public class GoogleOauthController {

    private final String baseUrl;
    private final AuthorizationCodeFlow authCodeFlow;

    public GoogleOauthController(@Value("${base-url}") String baseUrl,
                                 AuthorizationCodeFlow authCodeFlow) {
        this.baseUrl = baseUrl;
        this.authCodeFlow = authCodeFlow;
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
    public String callback(String code) throws IOException {

        var tokenResponse = authCodeFlow.newTokenRequest(code)
                .setRedirectUri(callbackUrl())
                .execute();

        var accessToken = tokenResponse.getAccessToken();
        var refreshToken = tokenResponse.getRefreshToken();

        return "";
    }

    private String callbackUrl() {
        return baseUrl + Api.API_PREFIX + "/oauth2/callback";
    }

}
