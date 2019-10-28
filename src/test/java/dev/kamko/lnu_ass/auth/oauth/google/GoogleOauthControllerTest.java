package dev.kamko.lnu_ass.auth.oauth.google;

import java.util.Map;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import dev.kamko.lnu_ass.auth.oauth.google.dto.GoogleTokens;
import dev.kamko.lnu_ass.config.Api;
import dev.kamko.lnu_ass.core.domain.user.aggregate.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GoogleOauthControllerTest {

    private GoogleOauthController sut;
    private AuthorizationCodeFlow flow;
    private UserService userService;

    private static String BASE_URL = "https://base-uri";

    @BeforeEach
    private void setUp() {
        userService = mock(UserService.class);
        flow = mock(AuthorizationCodeFlow.class);
        sut = new GoogleOauthController(BASE_URL, flow, userService);
    }

    @Test
    void initiate() {
        // AuthorizationCodeFlow contains final methods which can be mocked/stubbed only using mockito extensions!
        // the result is brittle test - but google is not allowing automatic tests of the login flow.
        var mockUri = mock(AuthorizationCodeRequestUrl.class);
        var googleUrl = "google.com/lalala";

        when(flow.newAuthorizationUrl()).thenReturn(mockUri);
        when(mockUri.setRedirectUri(BASE_URL + Api.AUTH_PREFIX + "/oauth2/callback")).thenReturn(mockUri);
        when(mockUri.build()).thenReturn(googleUrl);

        var result = sut.initiate();

        assertThat(result).isInstanceOf(Map.class);
        //noinspection unchecked
        assertThat((Map) result).contains(Map.entry("redirectTo", googleUrl));
    }

    @Test
    void callback() throws Exception {
        var code = "CODE";
        var accessToken = "accessToken";
        var refreshToken = "refreshToken";

        var mockReq = mock(AuthorizationCodeTokenRequest.class);

        var stubResponse = mock(TokenResponse.class);
        when(stubResponse.getAccessToken()).thenReturn(accessToken);
        when(stubResponse.getRefreshToken()).thenReturn(refreshToken);

        when(flow.newTokenRequest(code)).thenReturn(mockReq);
        when(mockReq.setRedirectUri(BASE_URL + Api.AUTH_PREFIX + "/oauth2/callback")).thenReturn(mockReq);
        when(mockReq.execute()).thenReturn(stubResponse);

        sut.callback(code);

        verify(userService, times(1))
                .handleUserAuthentication(GoogleTokens.of(accessToken, refreshToken));
    }
}