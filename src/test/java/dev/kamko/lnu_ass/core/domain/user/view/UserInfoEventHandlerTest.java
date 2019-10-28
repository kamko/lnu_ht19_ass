package dev.kamko.lnu_ass.core.domain.user.view;

import java.time.LocalDateTime;
import java.util.Optional;

import dev.kamko.lnu_ass.TestTemporalConfiguration;
import dev.kamko.lnu_ass.auth.oauth.google.dto.GoogleTokens;
import dev.kamko.lnu_ass.core.domain.user.aggregate.UserAggregateRepo;
import dev.kamko.lnu_ass.core.domain.user.aggregate.UserService;
import dev.kamko.lnu_ass.core.domain.user.command.RegisterUserCommand;
import io.eventuate.SaveOptions;
import io.eventuate.javaclient.spring.jdbc.EmbeddedTestAggregateStoreConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest()
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@Import({
        TestTemporalConfiguration.class,
        EmbeddedTestAggregateStoreConfiguration.class
})
class UserInfoEventHandlerTest {

    @SpyBean
    private UserInfoEventHandler sut;

    @Autowired
    private UserAggregateRepo aggregateRepo;

    @MockBean
    private UserInfoService userInfoService;

    @Test
    void registerAndLoginEventsAreHandled() throws Exception {
        var id = "id";
        var cmd = new RegisterUserCommand("name", "email", LocalDateTime.now(), "efg");

        aggregateRepo.save(cmd, Optional.of(new SaveOptions().withId(id))).join();

        // wait for event handler to be called by eventuate
        Thread.sleep(3000);

        verify(sut, times(1)).register(any());
        verify(sut, times(1)).login(any());

        verify(userInfoService, times(1))
                .handleNew(new UserInfo(id, cmd.getName(), cmd.getEmail(), cmd.getRegistrationTime()));
        verify(userInfoService, times(1))
                .markLogin(id, cmd.getRegistrationTime());
    }
}