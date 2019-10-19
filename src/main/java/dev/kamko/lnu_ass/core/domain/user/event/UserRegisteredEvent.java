package dev.kamko.lnu_ass.core.domain.user.event;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisteredEvent implements UserEvent {
    String name;
    String email;
    LocalDateTime registeredAt;
}
