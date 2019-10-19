package dev.kamko.lnu_ass.core.user.domain.event;

import io.eventuate.Event;
import io.eventuate.EventEntity;

@EventEntity(entity = "dev.kamko.lnu_ass.core.user.domain.User")
public interface UserEvent extends Event {
}
