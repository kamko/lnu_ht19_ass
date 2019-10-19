package dev.kamko.lnu_ass.core.domain.user.view;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    void updateLogin(String id, LocalDateTime time) {
        var ui = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("No UserInfo with id " + id + " found!"));

        ui.setLastLoginAt(time);

        repo.save(ui);
    }
}
