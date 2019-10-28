package dev.kamko.lnu_ass.core.domain.user.aggregate;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import dev.kamko.lnu_ass.TestTemporalConfiguration;
import dev.kamko.lnu_ass.auth.oauth.google.dto.GoogleTokens;
import dev.kamko.lnu_ass.auth.token.JwtTokenService;
import dev.kamko.lnu_ass.core.domain.user.event.UserLoginEvent;
import dev.kamko.lnu_ass.core.domain.user.event.UserRefreshTokenReceivedEvent;
import dev.kamko.lnu_ass.core.domain.user.event.UserRegisteredEvent;
import dev.kamko.lnu_ass.core.google.user.GoogleUserInfo;
import dev.kamko.lnu_ass.core.google.user.GoogleUserService;
import io.eventuate.EventWithMetadata;
import io.eventuate.javaclient.spring.jdbc.EmbeddedTestAggregateStoreConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest()
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@Import({
        TestTemporalConfiguration.class,
        EmbeddedTestAggregateStoreConfiguration.class
})
class UserServiceTest {

    @Autowired
    private UserService sut;

    @Autowired
    private UserAggregateRepo aggregateRepo;

    @Autowired
    private JwtTokenService jwtTokenService;

    @MockBean
    private GoogleUserService googleUserService;

    @Autowired
    private Clock clock;

    @BeforeEach
    private void setUp() {
    }

    @Test
    void handleNewUser() {
        var ginfo = googleUserInfo("sub-1");

        when(googleUserService.getUserInfo(any()))
                .thenReturn(ginfo);

        var result = sut.handleUserAuthentication(GoogleTokens.of("abc", "efg")).join();
        var resToken = (String) result.get("jwtToken");

        assertThat(jwtTokenService.isValidToken(resToken)).isTrue();

        var aggregate = aggregateRepo.find(ginfo.getSub()).join();
        assertThat(aggregate.getEvents().stream().map(EventWithMetadata::getEvent).collect(Collectors.toList()))
                .containsExactlyInAnyOrder(
                        new UserRegisteredEvent(ginfo.getName(), ginfo.getEmail(), LocalDateTime.now(clock)),
                        new UserRefreshTokenReceivedEvent("efg"),
                        new UserLoginEvent(LocalDateTime.now(clock))
                );
    }

    @Test
    void handleKnownUser() {
        var ginfo = googleUserInfo("sub-2");

        when(googleUserService.getUserInfo(any()))
                .thenReturn(ginfo);

        sut.handleUserAuthentication(GoogleTokens.of("abc", "efg")).join();
        var result = sut.handleUserAuthentication(GoogleTokens.of("abc", "efg")).join();

        // expect 2 logins
        var aggregate = aggregateRepo.find(ginfo.getSub()).join();
        assertThat(aggregate.getEvents().stream().map(EventWithMetadata::getEvent).collect(Collectors.toList()))
                .containsExactlyInAnyOrder(
                        new UserRegisteredEvent(ginfo.getName(), ginfo.getEmail(), LocalDateTime.now(clock)),
                        new UserRefreshTokenReceivedEvent("efg"),
                        new UserLoginEvent(LocalDateTime.now(clock)),
                        new UserLoginEvent(LocalDateTime.now(clock))
                );
    }

    private GoogleUserInfo googleUserInfo(String sub) {
        return new GoogleUserInfo(sub, "First Last", sub + "@student.lnu.se");
    }
}