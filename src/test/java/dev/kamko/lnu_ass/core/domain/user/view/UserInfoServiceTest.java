package dev.kamko.lnu_ass.core.domain.user.view;

import java.time.LocalDateTime;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserInfoServiceTest {

    private UserInfoService sut;
    private UserInfoRepository userInfoRepo;

    @BeforeEach
    private void setUp() {
        userInfoRepo = mock(UserInfoRepository.class);
        sut = new UserInfoService(userInfoRepo);
    }

    @Test
    void listAll() {
        sut.listAll();

        verify(userInfoRepo, only()).findAll();
    }

    @Test
    void handleNew() {
        var info = new UserInfo("abc", "abc", "abc", LocalDateTime.now());

        sut.handleNew(info);

        verify(userInfoRepo, only()).save(info);
    }

    @Test
    void markLoginFailsWhenInfoDoesNotExists() {
        var id = "ABC";
        var time = LocalDateTime.now();

        when(userInfoRepo.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.markLogin(id, time))
                .isInstanceOf(UserInfoService.UserNotFound.class);
    }

    @Test
    void markLogin() {
        var id = "abc";
        var registrationTime = LocalDateTime.now();
        var loginTime = LocalDateTime.now().plusHours(1);

        var oldVal = new UserInfo("abc", "abc", "abc", registrationTime);
        var newVal = new UserInfo("abc", "abc", "abc", registrationTime);
        newVal.setLastLoginAt(loginTime);

        when(userInfoRepo.findById(id)).thenReturn(Optional.of(oldVal));

        sut.markLogin(id, loginTime);

        verify(userInfoRepo, times(1)).save(newVal);
    }
}