package dev.kamko.lnu_ass.core.domain.user;

import java.util.List;

import dev.kamko.lnu_ass.core.domain.user.command.RegisterUserCommand;
import dev.kamko.lnu_ass.core.domain.user.command.UserCommand;
import dev.kamko.lnu_ass.core.domain.user.event.UserRefreshTokenReceivedEvent;
import dev.kamko.lnu_ass.core.domain.user.event.UserRegisteredEvent;
import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends ReflectiveMutableCommandProcessingAggregate<User, UserCommand> {
    private String googleId;
    private String name;
    private String email;
    private String encryptedRefreshToken;

    public List<Event> process(RegisterUserCommand cmd) {
        return EventUtil.events(
                new UserRegisteredEvent(
                        cmd.getName(), cmd.getEmail(), cmd.getSud()),
                new UserRefreshTokenReceivedEvent(
                        cmd.getEncryptedRefreshToken())
        );
    }

    public void apply(UserRegisteredEvent event) {
        log.trace("apply(event={})", event);

        this.googleId = event.getGoogleId();
        this.name = event.getName();
        this.email = event.getEmail();
    }

    public void apply(UserRefreshTokenReceivedEvent event) {
        log.trace("apply(event={})", event);

        this.encryptedRefreshToken = event.getEncryptedRefreshToken();
    }

}
