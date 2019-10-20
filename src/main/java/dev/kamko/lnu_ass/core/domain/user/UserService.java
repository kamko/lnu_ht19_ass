package dev.kamko.lnu_ass.core.domain.user;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import dev.kamko.lnu_ass.auth.JwtTokenService;
import dev.kamko.lnu_ass.auth.oauth.google.dto.GoogleTokens;
import dev.kamko.lnu_ass.core.domain.user.command.LoginUserCommand;
import dev.kamko.lnu_ass.core.domain.user.command.RegisterUserCommand;
import dev.kamko.lnu_ass.core.google.user.GoogleUserInfo;
import dev.kamko.lnu_ass.core.google.user.GoogleUserService;
import io.eventuate.EntityNotFoundException;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.SaveOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final UserAggregateRepo userRepo;
    private final GoogleUserService googleUserService;
    private final JwtTokenService jwtTokenService;

    public UserService(UserAggregateRepo userRepo,
                       GoogleUserService googleUserService,
                       JwtTokenService jwtTokenService) {
        this.userRepo = userRepo;
        this.googleUserService = googleUserService;
        this.jwtTokenService = jwtTokenService;
    }

    public CompletableFuture<Map<String, Object>>
    handleUserAuthentication(GoogleTokens tokens) {
        var googleInfo = googleUserService.getUserInfo(tokens.getAccessToken());
        log.trace("handleUserAuthentication(sub={})", googleInfo.getSub());

        var email = googleInfo.getEmail();
        try {
            // TODO find better way
            var agg = userRepo.find(googleInfo.getSub()).join();
            return addJwtToken(email, loginUser(googleInfo));
        } catch (CompletionException e) {
            if (e.getCause() instanceof EntityNotFoundException) {
                log.trace("New user found (id={}), registering.", googleInfo.getSub());
                return addJwtToken(email, registerUser(googleInfo, tokens));
            }
            throw e;
        }
    }

    private CompletableFuture<Map<String, Object>> addJwtToken(String email, CompletableFuture<?> future) {
        return future.thenApply(
                ewiv -> Map.of(
                        "jwtToken", jwtTokenService.generateToken(email),
                        "entity", ewiv));
    }

    private CompletableFuture<EntityWithIdAndVersion<User>> registerUser(GoogleUserInfo googleInfo,
                                                                         GoogleTokens tokens) {
        var cmd = new RegisterUserCommand(
                googleInfo.getName(),
                googleInfo.getEmail(),
                LocalDateTime.now(),
                tokens.getRefreshToken());
        return userRepo.save(cmd, saveOptionsWithId(googleInfo.getSub()));
    }

    private CompletableFuture<EntityWithIdAndVersion<User>> loginUser(GoogleUserInfo googleInfo) {
        return userRepo.update(googleInfo.getSub(), new LoginUserCommand(LocalDateTime.now()));
    }

    private Optional<SaveOptions> saveOptionsWithId(String id) {
        var so = new SaveOptions();
        so.withId(id);
        return Optional.of(so);
    }
}
