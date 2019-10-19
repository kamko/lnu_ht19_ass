package dev.kamko.lnu_ass.core.domain.user.view;

import dev.kamko.lnu_ass.core.domain.user.event.UserLoginEvent;
import dev.kamko.lnu_ass.core.domain.user.event.UserRegisteredEvent;
import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import org.springframework.stereotype.Component;

@Component
@EventSubscriber(id = "userInfoEventHandler")
public class UserInfoEventHandler {

    private final UserInfoService userInfoService;

    public UserInfoEventHandler(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @EventHandlerMethod
    public void register(DispatchedEvent<UserRegisteredEvent> de) {
        userInfoService.handleNew(
                new UserInfo(
                        de.getEntityId(),
                        de.getEvent().getName(),
                        de.getEvent().getEmail(),
                        de.getEvent().getRegisteredAt()));
    }

    @EventHandlerMethod
    public void login(DispatchedEvent<UserLoginEvent> de) {
        userInfoService.updateLogin(de.getEntityId(), de.getEvent().getLoginTime());
    }
}
