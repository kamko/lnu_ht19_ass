package dev.kamko.lnu_ass.core.domain.user.command;

import lombok.Value;

@Value
public class UserAuthenticatedCommand implements UserCommand {
    String name;
    String email;
    String encryptedRefreshToken;
}
