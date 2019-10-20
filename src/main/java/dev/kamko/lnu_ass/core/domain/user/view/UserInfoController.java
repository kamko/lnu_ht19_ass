package dev.kamko.lnu_ass.core.domain.user.view;

import java.util.List;

import dev.kamko.lnu_ass.config.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Api.API_PREFIX + "/users")
public class UserInfoController {

    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping
    public List<UserInfo> fetchAllUsers() {
        return userInfoService.listAll();
    }

}
