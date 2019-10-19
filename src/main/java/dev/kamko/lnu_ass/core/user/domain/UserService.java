package dev.kamko.lnu_ass.core.user.domain;

import java.util.concurrent.CompletableFuture;

import dev.kamko.lnu_ass.core.user.domain.command.RegisterUserCommand;
import dev.kamko.lnu_ass.core.user.domain.command.UserCommand;
import dev.kamko.lnu_ass.core.user.google.GoogleUserService;
import dev.kamko.lnu_ass.crypto.EncryptionService;
import dev.kamko.lnu_ass.oauth.google.dto.GoogleTokens;
import io.eventuate.AggregateRepository;
import io.eventuate.EntityWithIdAndVersion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final AggregateRepository<User, UserCommand> userRepo;
    private final GoogleUserService googleUserService;
    private final EncryptionService encryptionService;

    public UserService(AggregateRepository<User, UserCommand> userRepo,
                       GoogleUserService googleUserService,
                       EncryptionService encryptionService) {
        this.userRepo = userRepo;
        this.googleUserService = googleUserService;
        this.encryptionService = encryptionService;
    }

    public CompletableFuture<EntityWithIdAndVersion<User>>
    registerUser(GoogleTokens tokens) {
        var googleInfo = googleUserService.getUserInfo(tokens.getAccessToken());
        log.trace("registerUser(email={})", googleInfo.getEmail());

        return userRepo.save(
                new RegisterUserCommand(
                        googleInfo.getName(),
                        googleInfo.getEmail(),
                        googleInfo.getSub(),
                        encryptionService.encryptString(tokens.getRefreshToken()))
        );
    }
}
