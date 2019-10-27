package dev.kamko.lnu_ass.core.domain.user.view;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class UserInfoService {

    private final UserInfoRepository repo;

    public UserInfoService(UserInfoRepository repo) {
        this.repo = repo;
    }

    List<UserInfo> listAll() {
        return repo.findAll();
    }

    @Transactional
    void handleNew(UserInfo ui) {
        repo.save(ui);
    }

    @Transactional
    void markLogin(String id, LocalDateTime time) {
        var ui = repo.findById(id)
                .orElseThrow(() -> new UserNotFound(id));

        ui.setLastLoginAt(time);

        repo.save(ui);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    static class UserNotFound extends RuntimeException {

        public UserNotFound(String id) {
            super("No UserInfo with id " + id + " found!");
        }
    }
}
