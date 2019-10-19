package dev.kamko.lnu_ass.core.domain.user.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRefreshTokenReceivedEvent implements UserEvent {
    String encryptedRefreshToken;
}
