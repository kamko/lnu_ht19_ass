package dev.kamko.lnu_ass.core.domain.user.event;

import java.time.LocalDateTime;

import lombok.Value;

@Value
public class UserLoginEvent implements UserEvent {
    LocalDateTime loginTime;
}
