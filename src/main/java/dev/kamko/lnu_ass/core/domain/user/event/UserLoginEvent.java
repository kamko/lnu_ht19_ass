package dev.kamko.lnu_ass.core.domain.user.event;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginEvent implements UserEvent {
    LocalDateTime loginTime;
}
