package dev.kamko.lnu_ass.core.user.domain.event;

import lombok.Value;

@Value
public class UserRefreshTokenReceivedEvent implements UserEvent {
    String encryptedRefreshToken;
}
