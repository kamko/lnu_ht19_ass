package dev.kamko.lnu_ass.core.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import dev.kamko.lnu_ass.core.domain.user.command.LoginUserCommand;
import dev.kamko.lnu_ass.core.domain.user.command.RegisterUserCommand;
import dev.kamko.lnu_ass.core.domain.user.command.UserCommand;
import dev.kamko.lnu_ass.core.domain.user.event.UserLoginEvent;
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

    private String name;
    private String email;
    private String encryptedRefreshToken;
    private LocalDateTime registeredAt;
    private LocalDateTime lastLoginAt;

    public List<Event> process(RegisterUserCommand cmd) {
        return EventUtil.events(
                new UserRegisteredEvent(
                        cmd.getName(), cmd.getEmail(), cmd.getRegistrationTime()),
                new UserRefreshTokenReceivedEvent(
                        cmd.getEncryptedRefreshToken()),
                new UserLoginEvent(cmd.getRegistrationTime())
        );
    }

    public List<Event> process(LoginUserCommand cmd) {
        return EventUtil.events(
                new UserLoginEvent(cmd.getLoginTime())
        );
    }

    public void apply(UserRegisteredEvent event) {
        log.trace("apply(event={})", event);

        this.name = event.getName();
        this.email = event.getEmail();
        this.registeredAt = event.getRegisteredAt();
    }

    public void apply(UserRefreshTokenReceivedEvent event) {
        log.trace("apply(event={})", event);

        this.encryptedRefreshToken = event.getEncryptedRefreshToken();
    }

    public void apply(UserLoginEvent event) {
        log.trace("apply(event={}", event);

        this.lastLoginAt = event.getLoginTime();
    }

}
