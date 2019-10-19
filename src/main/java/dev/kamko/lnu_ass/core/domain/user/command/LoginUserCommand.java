package dev.kamko.lnu_ass.core.domain.user.command;

import java.time.LocalDateTime;

import lombok.Value;

@Value
public class LoginUserCommand implements UserCommand {
    LocalDateTime loginTime;
}
