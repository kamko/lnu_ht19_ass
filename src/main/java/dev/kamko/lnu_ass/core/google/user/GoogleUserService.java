package dev.kamko.lnu_ass.core.google.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class GoogleUserService {

    static final String USER_INFO_ENDPOINT = "https://www.googleapis.com/oauth2/v3/userinfo";

    private final RestTemplate rt;

    public GoogleUserService(RestTemplateBuilder builder) {
        this.rt = builder.build();
    }

    public GoogleUserInfo getUserInfo(String accessToken) {
        log.trace("getUserInfo(...)");
        var httpEntity = new HttpEntity<GoogleUserInfo>(headers(accessToken));
        var responseEntity = rt.exchange(
                USER_INFO_ENDPOINT,
                HttpMethod.GET,
                httpEntity,
                GoogleUserInfo.class
        );

        return responseEntity.getBody();
    }

    private HttpHeaders headers(String token) {
        var headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return headers;
    }

}
