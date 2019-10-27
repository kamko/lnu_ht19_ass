package dev.kamko.lnu_ass.core.domain.user.aggregate;

import dev.kamko.lnu_ass.core.domain.user.command.UserCommand;
import io.eventuate.AggregateRepository;
import io.eventuate.EventuateAggregateStore;
import org.springframework.stereotype.Component;

@Component
public class UserAggregateRepo extends AggregateRepository<User, UserCommand> {

    public UserAggregateRepo(EventuateAggregateStore aggregateStore) {
        super(User.class, aggregateStore);
    }
}
