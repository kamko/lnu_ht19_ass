package dev.kamko.lnu_ass.core.domain.user;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import dev.kamko.lnu_ass.core.domain.user.command.UserAuthenticatedCommand;
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

        return userRepo.save(
                new UserAuthenticatedCommand(
                        googleInfo.getName(),
                        googleInfo.getEmail(),
                        encryptionService.encryptString(tokens.getRefreshToken())),
                saveOptionsWithId(googleInfo.getSub())
        );
    }

    private Optional<SaveOptions> saveOptionsWithId(String id) {
        var so = new SaveOptions();
        so.withId(id);
        return Optional.of(so);
    }
}
