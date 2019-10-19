package dev.kamko.lnu_ass.core.domain.user;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import dev.kamko.lnu_ass.core.domain.user.command.LoginUserCommand;
import dev.kamko.lnu_ass.core.domain.user.command.RegisterUserCommand;
import dev.kamko.lnu_ass.core.google.user.GoogleUserInfo;
import dev.kamko.lnu_ass.core.google.user.GoogleUserService;
import dev.kamko.lnu_ass.crypto.EncryptionService;
import dev.kamko.lnu_ass.oauth.google.dto.GoogleTokens;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.SaveOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final UserAggregateRepo userRepo;
    private final GoogleUserService googleUserService;
    private final EncryptionService encryptionService;

    public UserService(UserAggregateRepo userRepo,
                       GoogleUserService googleUserService,
                       EncryptionService encryptionService) {
        this.userRepo = userRepo;
        this.googleUserService = googleUserService;
        this.encryptionService = encryptionService;
    }

    public CompletableFuture<EntityWithIdAndVersion<User>>
    handleUserAuthentication(GoogleTokens tokens) {
        var googleInfo = googleUserService.getUserInfo(tokens.getAccessToken());
        log.trace("registerUser(email={})", googleInfo.getEmail());

        var agg = userRepo.find(googleInfo.getSub());

        if (agg == null) {
            return registerUser(googleInfo, tokens);
        } else {
            return loginUser(googleInfo);
        }
    }

    private CompletableFuture<EntityWithIdAndVersion<User>>
    registerUser(GoogleUserInfo googleInfo, GoogleTokens tokens) {
        var cmd = new RegisterUserCommand(
                googleInfo.getName(),
                googleInfo.getEmail(),
                LocalDateTime.now(),
                encryptionService.encryptString(tokens.getRefreshToken()));
        return userRepo.save(cmd, saveOptionsWithId(googleInfo.getSub()));
    }

    private CompletableFuture<EntityWithIdAndVersion<User>>
    loginUser(GoogleUserInfo googleInfo) {
        return userRepo.update(googleInfo.getSub(), new LoginUserCommand(LocalDateTime.now()));
    }

    private Optional<SaveOptions> saveOptionsWithId(String id) {
        var so = new SaveOptions();
        so.withId(id);
        return Optional.of(so);
    }
}
