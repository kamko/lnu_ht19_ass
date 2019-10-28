package dev.kamko.lnu_ass.core.domain.user.view;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class UserInfoControllerTest {

    private MockMvc mvc;
    private ObjectMapper om = new ObjectMapper();

    private UserInfoService userInfoService;

    @BeforeEach
    private void setUp() {
        om.registerModule(new JavaTimeModule());

        userInfoService = mock(UserInfoService.class);
        UserInfoController sut = new UserInfoController(userInfoService);
        mvc = MockMvcBuilders.standaloneSetup(sut)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(om))
                .build();
    }

    @Test
    void fetchAllUsers() throws Exception {
        var infos = List.of(
                new UserInfo("id", "name", "email", LocalDateTime.now()),
                new UserInfo("id2", "name", "email", LocalDateTime.now())
        );

        when(userInfoService.listAll()).thenReturn(infos);

        var resultString = mvc.perform(get("/user-service/users"))
                .andReturn().getResponse().getContentAsString();

        var result = om.readValue(resultString, new TypeReference<List<UserInfo>>() {
        });

        Assertions.assertThat(result).isEqualTo(infos);
        verify(userInfoService, only()).listAll();
    }
}