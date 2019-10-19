package dev.kamko.lnu_ass.core.user.domain.command;

import lombok.Value;

@Value
public class RegisterUserCommand implements UserCommand {
    String name;
    String email;
    String sud;
    String encryptedRefreshToken;
}
