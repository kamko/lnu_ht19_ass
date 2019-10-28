package dev.kamko.lnu_ass.auth.token;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class AuthTokenControllerTest {

    private MockMvc mvc;
    private ObjectMapper om = new ObjectMapper();

    private JwtTokenService jwtTokenService;

    @BeforeEach
    private void setUp() {
        jwtTokenService = mock(JwtTokenService.class);

        AuthTokenController sut = new AuthTokenController(jwtTokenService);
        mvc = MockMvcBuilders.standaloneSetup(sut)
                .build();
    }

    @Test
    void verifyToken() throws Exception {
        var token = "TOKEN-12345";

        when((jwtTokenService.isValidToken(token))).thenReturn(true);

        var resultString = mvc.perform(get("/user-service/auth/token/{token}", token))
                .andReturn().getResponse().getContentAsString();

        var result = om.readValue(resultString, new TypeReference<Map<String, Object>>() {
        });

        assertThat(result)
                .contains(Map.entry("token", token))
                .contains(Map.entry("valid", true));
    }
}