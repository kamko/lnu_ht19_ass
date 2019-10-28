package dev.kamko.lnu_ass.core.domain.user.view;

import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserInfoControllerTest {

    private UserInfoController sut;
    private UserInfoService userInfoService;

    @BeforeEach
    private void setUp() {
        userInfoService = mock(UserInfoService.class);
        sut = new UserInfoController(userInfoService);
    }

    @Test
    void fetchAllUsers() {
        var infos = List.of(
                new UserInfo("id", "name", "email", LocalDateTime.now()),
                new UserInfo("id2", "name", "email", LocalDateTime.now())
        );

        when(userInfoService.listAll()).thenReturn(infos);

        var result = sut.fetchAllUsers();

        Assertions.assertThat(result).isEqualTo(infos);
        verify(userInfoService, only()).listAll();
    }
}