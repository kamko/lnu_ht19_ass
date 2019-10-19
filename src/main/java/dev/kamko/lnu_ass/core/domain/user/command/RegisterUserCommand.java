package dev.kamko.lnu_ass.core.domain.user.command;

import java.time.LocalDateTime;

import lombok.Value;

@Value
public class RegisterUserCommand implements UserCommand {
    String name;
    String email;
    LocalDateTime registrationTime;
    String encryptedRefreshToken;
}
