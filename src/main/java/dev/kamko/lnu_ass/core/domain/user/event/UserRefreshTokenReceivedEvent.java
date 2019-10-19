package dev.kamko.lnu_ass.core.domain.user.event;

import lombok.Value;

@Value
public class UserRefreshTokenReceivedEvent implements UserEvent {
    String encryptedRefreshToken;
}
