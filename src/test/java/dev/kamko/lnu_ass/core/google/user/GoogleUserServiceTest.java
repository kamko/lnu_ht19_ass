package dev.kamko.lnu_ass.core.google.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(GoogleUserService.class)
@ExtendWith(SpringExtension.class)
class GoogleUserServiceTest {

    @Autowired
    private GoogleUserService sut;

    @Autowired
    private MockRestServiceServer server;

    @Test
    void getUserInfo() {
        var token = "ABCDEF123456789";
        var googleResponse = "{\n" +
                "  \"sub\": \"SUB123\",\n" +
                "  \"name\": \"First Last\",\n" +
                "  \"given_name\": \"First\",\n" +
                "  \"family_name\": \"Last\",\n" +
                "  \"picture\": \"https://........\",\n" +
                "  \"email\": \"first_last@student.lnu.se\",\n" +
                "  \"email_verified\": true,\n" +
                "  \"locale\": \"en-GB\",\n" +
                "  \"hd\": \"student.lnu.se\"\n" +
                "}";
        var userInfo = new GoogleUserInfo("SUB123", "First Last", "first_last@student.lnu.se");

        server.expect(requestTo(GoogleUserService.USER_INFO_ENDPOINT))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("Authorization", "Bearer " + token))
                .andRespond(withSuccess(googleResponse, MediaType.APPLICATION_JSON));

        var result = sut.getUserInfo(token);

        server.verify();
        assertThat(result).isEqualTo(userInfo);
    }
}