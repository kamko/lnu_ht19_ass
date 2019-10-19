package dev.kamko.lnu_ass.core.user.domain.event;

import lombok.Value;

@Value
public class UserRegisteredEvent implements UserEvent {
    String name;
    String email;
    String googleId;
}
