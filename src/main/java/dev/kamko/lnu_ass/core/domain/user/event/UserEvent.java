package dev.kamko.lnu_ass.core.domain.user.event;

import io.eventuate.Event;
import io.eventuate.EventEntity;

@EventEntity(entity = "dev.kamko.lnu_ass.core.domain.user.aggregate.User")
public interface UserEvent extends Event {
}
