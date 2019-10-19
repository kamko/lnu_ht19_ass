package dev.kamko.lnu_ass.core.domain.user.event;

import java.time.LocalDateTime;

import lombok.Value;

@Value
public class UserRegisteredEvent implements UserEvent {
    String name;
    String email;
    LocalDateTime registeredAt;
}
